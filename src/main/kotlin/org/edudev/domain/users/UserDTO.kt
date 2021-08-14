package org.edudev.domain.users

import org.edudev.arch.extensions.throwIfViolate
import org.edudev.arch.extensions.validate
import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.ProfileDTO
import java.util.*

data class UserDTO(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val profile: ProfileDTO?
) {

    constructor(user: User) : this(
        user.id,
        user.name,
        user.username,
        user.email,
        user.password,
        user.profile?.let { ProfileDTO(it) }
    )

    fun update(
        user: User,
        profileSearch: (String) -> Profile?
    ) = user.also { u ->
        require(u.id == id) { "Incompatible Id" }
        u.name = name
        u.username = username
        u.email = email
        u.password = password
        u.profile = profile?.id?.let { profileSearch(it) }
        u.validate().throwIfViolate()
    }

}

fun User.toDTO() = UserDTO(this)

