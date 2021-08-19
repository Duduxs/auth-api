package org.edudev.domain.users.profiles

import org.edudev.arch.auth.functionality.GlobalFunctionality.PROPERTIES
import org.edudev.arch.auth.functionality.GlobalFunctionality.USERS
import org.edudev.arch.auth.functionality.action.CrudAction.*
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.ProfileDTO
import org.junit.Test

class ProfileDTOTest {

    private val permissions = listOf(
        Permission(USERS, listOf(INSERT, READ)),
        Permission(PROPERTIES, listOf(INSERT, READ, DELETE, UPDATE))
    )

    private val profile = Profile().also {
        it.name = "Employee Profile"
        it.permissions = permissions
    }

    @Test
    fun `ProfileDTO must be instantiable`(){
        val dto = ProfileDTO(profile)
        profile assertEquals dto
    }

    @Test
    fun `Must update User in update method`(){
        val dto = ProfileDTO(profile)
        val newProfile = Profile(dto.id)

        dto.update(newProfile)
        newProfile assertEquals dto

    }
}