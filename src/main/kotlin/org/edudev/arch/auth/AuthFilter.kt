package org.edudev.arch.auth

import mu.KLogging
import org.edudev.arch.auth.HTTPVerb.*
import org.edudev.arch.auth.functionality.GlobalFunctionality.EMPTY
import org.edudev.arch.auth.functionality.action.CrudAction
import org.edudev.arch.auth.functionality.action.CrudAction.*
import org.edudev.arch.exceptions.ForbiddenHttpException
import org.edudev.arch.exceptions.UnauthorizedHttpException
import org.edudev.arch.extensions.decodeBase64Authorization
import org.edudev.arch.extensions.isNullOrEmpty
import org.edudev.arch.extensions.orThrowForbidden
import org.edudev.domain.users.User
import org.edudev.domain.users.Users
import java.util.*
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.Context
import javax.ws.rs.ext.Provider

@Provider
@Restricted(EMPTY)
open class AuthFilter : ContainerRequestFilter {

    private val authorizationHeader = "Authorization"

    @Context
    lateinit var resourceInfo: ResourceInfo

    @Inject
    lateinit var users: Users

    override fun filter(requestContext: ContainerRequestContext) {
        val loggedUser = performAuthentication(requestContext)

        performAuthorization(currentUser = loggedUser)

        buildSecurityContext(requestContext, loggedUser)
    }

    private fun performAuthentication(requestContext: ContainerRequestContext): User {
        val base64Credentials = requestContext.getHeaderString(authorizationHeader) ?: throw UnauthorizedHttpException()

        val basicAuth: StringTokenizer = base64Credentials.decodeBase64Authorization()

        val username = basicAuth.nextToken()
        val password = basicAuth.nextToken()

        return users.findByUsernameAndPassword(username, password) ?: throw UnauthorizedHttpException()
    }

    private fun performAuthorization(currentUser: User) {
        val restricted = resourceInfo.resourceClass.getAnnotation(Restricted::class.java)
        if (restricted.isNullOrEmpty()) return

        val functionality = restricted.functionality

        val permission = currentUser
            .profile!!
            .findPermissionBy(functionality)
            ?: throw ForbiddenHttpException("O perfil de ${currentUser.username} não possui permissão a $functionality")

        when (returnHTTPVerbInCallerMethod()) {
            POST -> permission.containsAction(INSERT).orThrowForbidden(currentUser, INSERT, functionality)
            PUT, PATCH -> permission.containsAction(UPDATE).orThrowForbidden(currentUser, UPDATE, functionality)
            HTTPVerb.DELETE -> permission.containsAction(CrudAction.DELETE).orThrowForbidden(currentUser, CrudAction.DELETE, functionality)
            GET -> permission.containsAction(READ).orThrowForbidden(currentUser, READ, functionality)
            else -> throw IllegalArgumentException("Valores possíveis: ${HTTPVerb.values().joinToString()}")
        }
    }

    private fun returnHTTPVerbInCallerMethod(): HTTPVerb {
        val httpVerbsList = HTTPVerb.annotationHttpVerbs()
        val verb = httpVerbsList.first { resourceInfo.resourceMethod.isAnnotationPresent(it.java) }.simpleName!!

        return HTTPVerb.valueOf(verb)
    }

    private fun buildSecurityContext(requestContext: ContainerRequestContext, loggedUser: User) {
        requestContext.securityContext = AuthorizationSecurityContext(
            principal = AuthenticatedUser(loggedUser),
            secure = requestContext.securityContext.isSecure,
            schemeType = AuthScheme.BASIC
        )
    }
    companion object : KLogging()
}


