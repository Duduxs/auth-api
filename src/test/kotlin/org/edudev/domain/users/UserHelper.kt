package org.edudev.domain.users

import java.util.*

val userWithPopulatedValues = createUser(
    id = UUID.randomUUID().toString(),
    username = "Eduardo Jose",
    email = "duduxss3@gmail.com",
    password = "eduardo123"
)

fun createAdminUser() = createUser(
    username = "Duduxs",
    email = "Duduxss3@gmail.com",
    password = "123"
)

fun createUser(
    id: String = UUID.randomUUID().toString(),
    username: String = "",
    email: String = "",
    password: String = ""
) = User(
    id = id,
).also {
    it.username = username
    it.email = email
    it.password = password
}

fun createUserDTO(user: User) = UserDTO(user)