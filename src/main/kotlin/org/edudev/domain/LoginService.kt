package org.edudev.domain

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.edudev.arch.auth.AuthenticatedUser
import org.edudev.arch.auth.JWTSecret
import org.edudev.arch.auth.Restricted
import org.edudev.arch.auth.functionality.GlobalFunctionality.EMPTY
import org.edudev.arch.extensions.toDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders.AUTHORIZATION
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/login")
@Restricted(EMPTY)
class LoginService {

    @Context
    lateinit var securityContext: SecurityContext

    private val currentAuthenticated
        get() = securityContext.userPrincipal as AuthenticatedUser

    @Inject
    private lateinit var jwtSecret: JWTSecret

    @POST
    fun login(): Response {
        val token = generateToken()

        return Response.ok().header(AUTHORIZATION, "Bearer $token").entity(token).build()
    }

    @POST
    @Path("/refresh")
    fun refreshToken(): String = generateToken()

    private fun generateToken() = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtSecret.secret)
            .setHeaderParam("typ", "JWT")
            .setSubject(currentAuthenticated.user.username)
            .setIssuer("EduDev")
            .setIssuedAt(Date())
            .setExpiration(LocalDateTime.now().plusMinutes(60).toDate())
            .claim("roles", currentAuthenticated.user.profile!!.permissions)
            .compact()
    

}