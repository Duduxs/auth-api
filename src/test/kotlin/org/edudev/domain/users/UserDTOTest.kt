package org.edudev.domain.users

import org.edudev.arch.auth.functionality.GlobalFunctionality.USERS
import org.edudev.arch.auth.functionality.action.CrudAction.INSERT
import org.edudev.arch.auth.functionality.action.CrudAction.READ
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.domain.users.profile.Profile
import org.junit.Test

class UserDTOTest {

    private val profile = Profile().also {
        it.name = "Basic profile"
        it.permissions = listOf(
            Permission(USERS, listOf(INSERT, READ)),
        )
    }

    private val user = User().also {
        it.username = "Edward J"
        it.email = "edudev142@hotmail.com"
        it.password = "edu2445"
        it.profile = profile
    }

    @Test
    fun `UserDTO must be instantiable`(){
        val dto = UserDTO(user)
        user assertEquals dto
    }

    @Test
    fun `Must update User in update method`(){
        val dto = UserDTO(user)
        val newUser = User(dto.id)

        dto.update(newUser) { profileSearchMock(it) }
        newUser assertEquals dto

    }

    private fun profileSearchMock(id: String) = if(user.profile?.id == id) this.user.profile else null
}