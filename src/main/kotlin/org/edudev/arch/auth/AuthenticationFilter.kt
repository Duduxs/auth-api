package org.edudev.arch.auth

import mu.KLogging
import org.edudev.arch.extensions.decodeAsStringTokenizer
import java.util.*
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.ext.Provider

@Provider
class AuthenticationFilter : ContainerRequestFilter {

    private val authorizationHeader = "Authorization"
    private val basicAuthorizationPrefix = "Basic"

    override fun filter(requestContext: ContainerRequestContext) {

        val basicAuthorization: StringTokenizer = requestContext
            .getHeaderString(authorizationHeader)
            .replace(basicAuthorizationPrefix, "")
            .trim()
            .decodeAsStringTokenizer()

        logger.warn { basicAuthorization.nextToken() }
        logger.warn { basicAuthorization.nextToken() }
    }
    companion object : KLogging()
}


