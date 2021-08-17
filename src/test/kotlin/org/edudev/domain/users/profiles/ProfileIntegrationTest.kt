package org.edudev.domain.users.profiles

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.edudev.arch.auth.functionality.GlobalFunctionality.PROFILES
import org.edudev.arch.auth.functionality.GlobalFunctionality.USERS
import org.edudev.arch.auth.functionality.action.CrudAction.*
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.core.configs.QuarkusIntegrationTest
import org.edudev.domain.users.User
import org.edudev.domain.users.Users
import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.Profiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.inject.Inject


@QuarkusIntegrationTest
class ProfileIntegrationTest  {

    private val profile = Profile().also {
        it.name = "Employee Profile"
        it.permissions = listOf(
            Permission(USERS, listOf(INSERT, READ)),
            Permission(PROFILES, listOf(INSERT, READ, DELETE, UPDATE))
        )
    }

    private val user = User().also {
        it.name = "Eduardo J"
        it.username = "duduxs"
        it.email = "edudev142@hotmail.com"
        it.password = "edu2445"
        it.profile = profile
    }

    @Inject
    lateinit var profiles: Profiles

    @Inject
    lateinit var users: Users

    @BeforeAll
    private fun init(){
        profiles.insert(profile)
        users.insert(user)
    }

    @Test
    fun `Must throw forbidden by not has profile 'PROPERTIES'`() {
        Given {
            auth().preemptive().basic(user.username, user.password)
        } When {
            get("/properties")
        } Then {
            statusCode(403)
        }
    }

    @Test
    fun `Must return 200 by user authenticated`() {
        Given {
            auth().preemptive().basic(user.username, user.password)
        } When {
            get("/profiles")
        } Then {
            statusCode(200)
        }
    }


}