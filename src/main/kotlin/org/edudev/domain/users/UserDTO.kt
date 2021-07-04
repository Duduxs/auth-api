package org.edudev.domain.users

import org.edudev.arch.domain.NoArg
import java.util.*

@NoArg
data class UserDTO(
    val _id: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String,
    val password: String
) {
    constructor(user: User) : this(
        user._id,
        user.username,
        user.email,
        user.password
    )

    fun update(user: User) = user.also {
        require(it._id == _id) { "Incompatible Id" }
        it.username = username
        it.email = email
        it.password = password
    }
}