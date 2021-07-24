package org.edudev.domain.users

import org.edudev.core.configs.assertEquals
import org.junit.Test

class UserDTOTest {

    private val user = User().also {
        it.username = "Edward J"
        it.email = "edudev142@hotmail.com"
        it.password = "edu2445"
    }

    @Test
    fun `UserDTO must be instantiable`(){
        val dto = UserDTO(user)
        user.assertEquals(dto)
    }

    @Test
    fun `Must update User in update method`(){
        val dto = UserDTO(user)
        val newUser = User(dto.id)

        dto.update(newUser)
        newUser.assertEquals(dto)

    }
}