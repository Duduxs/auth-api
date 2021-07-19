package org.edudev.arch.auth

import org.edudev.arch.exceptions.UnauthorizedHttpException
import org.edudev.arch.extensions.decodeBase64Authorization
import org.edudev.domain.users.Users
import java.util.*
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.ext.Provider

@Provider
@Restricted
class AuthenticationFilter : ContainerRequestFilter {

    private val authorizationHeader = "Authorization"

    @Inject
    lateinit var users: Users

    override fun filter(requestContext: ContainerRequestContext) {

        val base64Credentials = requestContext.getHeaderString(authorizationHeader) ?: throw UnauthorizedHttpException()

        val basicAuth: StringTokenizer = base64Credentials.decodeBase64Authorization()

        validateCredentials(basicAuth.nextToken(), basicAuth.nextToken())
    }

    private fun validateCredentials(username: String, password: String) {
        if (users.existsByUsernameAndPassword(username, password)) return
        throw UnauthorizedHttpException()
    }
}


