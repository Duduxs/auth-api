package org.edudev.arch.auth

import org.edudev.arch.auth.functionality.GlobalFunctionality
import org.edudev.domain.users.User
import java.security.Principal

class AuthenticatedUser(
    val user: User
) : Principal {

    override fun getName() = user.username

    fun hasAuthorizedFor(functionality: GlobalFunctionality) = user.profile!!.hasPermissionBy(functionality)
}