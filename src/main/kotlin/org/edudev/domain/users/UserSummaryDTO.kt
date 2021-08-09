package org.edudev.domain.users

import org.edudev.domain.users.profile.Profile
import java.util.*

data class UserSummaryDTO(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String
)  {

    constructor(user: User) : this(
        user.id,
        user.username,
        user.email,
    )

    fun update(
        user: User,
    ) = user.also { u ->
        require(u.id == id) { "Incompatible Id" }
        u.username = username
        u.email = email
    }

}
