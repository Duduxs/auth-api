package org.edudev.arch.auth

import org.edudev.arch.auth.functionality.GlobalFunctionality.valueOf
import javax.ws.rs.core.SecurityContext

class AuthorizationSecurityContext(
    private val principal: AuthenticatedUser,
    private val secure: Boolean = false,
    private val schemeType: AuthScheme
) : SecurityContext {

    override fun getUserPrincipal() = principal

    override fun isUserInRole(functionality: String) = principal.hasAuthorizedFor(valueOf(functionality))

    override fun isSecure() = secure

    override fun getAuthenticationScheme() = schemeType.scheme
}