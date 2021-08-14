package org.edudev.core.security

import io.restassured.specification.RequestSpecification
import org.edudev.arch.auth.functionality.GlobalFunctionality
import org.edudev.arch.auth.functionality.GlobalFunctionality.*
import org.edudev.arch.auth.functionality.action.CrudAction
import org.edudev.arch.auth.functionality.action.CrudAction.*
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.domain.users.User
import org.edudev.domain.users.Users
import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.Profiles
import javax.inject.Inject

object DefaultAuth {

    @Inject
    lateinit var profiles: Profiles

    @Inject
    lateinit var users: Users

    private val adminProfile = Profile().also {
        it.permissions = listOf(
            Permission(USERS, listOf(INSERT, UPDATE, READ, DELETE)),
            Permission(PROFILES, listOf(INSERT, UPDATE, READ, DELETE)),
            Permission(PROPERTIES, listOf(INSERT, UPDATE, READ, DELETE))
        )
    }

    private val admin = User().apply {
        username = "admin"
        password = "123"
        profile = adminProfile
    }

    fun insertAdmin(){
        profiles.insert(adminProfile)
        users.insert(admin)
    }

    fun RequestSpecification.defaultAuthenticationHeader(){
        auth().preemptive().basic(admin.username, admin.password)
    }

}