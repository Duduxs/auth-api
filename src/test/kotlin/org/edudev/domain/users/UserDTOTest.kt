package org.edudev.domain.users

import org.edudev.core.helper.assertEquals
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class UserDTOTest {

    private val user = createUser(
        name = "Edward J",
        email = "edudev142@hotmail.com",
        password = "edu2445"
    )

    private val dto = createUserDTO(user)

    @Test
    fun `UserDTO must be instantiable`(){
        user.assertEquals(dto)
    }

    @Test
    fun `Must update User in update method`(){
        val newUser = createUser(dto._id)

        dto.update(newUser)
        newUser.assertEquals(dto)

    }
}