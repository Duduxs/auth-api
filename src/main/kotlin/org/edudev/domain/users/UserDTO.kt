package org.edudev.domain.users

import org.edudev.arch.domain.NoArg
import java.util.*

@NoArg
data class UserDTO(
    val _id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val password: String
) {
    constructor(user: User) : this(
        user._id,
        user.name,
        user.email,
        user.password
    )

    fun update(user: User) = user.also {
        require(it._id == _id) { "Incompatible Id" }
        it.name = name
        it.email = email
        it.password = password
    }
}