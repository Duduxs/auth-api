package org.edudev.domain.users

import java.util.*

val userWithPopulatedValues = createUser(
    _id = UUID.randomUUID().toString(),
    name = "Eduardo Jose",
    email = "duduxss3@gmail.com",
    password = "eduardo123"
)

fun createUser(
    _id: String = UUID.randomUUID().toString(),
    name: String = "",
    email: String = "",
    password: String = ""
) = User(
    _id = _id,
).also {
    it.name = name
    it.email = email
    it.password = password
}

fun createUserDTO(user: User) = UserDTO(user)