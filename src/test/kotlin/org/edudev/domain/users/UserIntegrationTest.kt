package org.edudev.domain.users

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.edudev.arch.auth.functionality.GlobalFunctionality.PROFILES
import org.edudev.arch.auth.functionality.GlobalFunctionality.USERS
import org.edudev.arch.auth.functionality.action.CrudAction.*
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.core.it.QuarkusIntegrationTest
import org.edudev.core.it.withCrudOperations
import org.edudev.core.it.withFindById
import org.edudev.core.security.DefaultAuth.defaultAuthenticationHeader
import org.edudev.core.security.DefaultAuth.insertAdmin
import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.Profiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.inject.Inject
import kotlin.reflect.KClass


@QuarkusIntegrationTest
@TestHTTPEndpoint(UsersService::class)
class UserIntegrationTest {

    @Inject
    private lateinit var profiles: Profiles

    @Inject
    private lateinit var users: Users

    private val employeeProfile = Profile().also {
        it.name = "Employee Profile"
        it.permissions = listOf(
            Permission(USERS, listOf(INSERT, READ)),
            Permission(
                PROFILES,
                listOf(INSERT, READ, DELETE, UPDATE)
            )
        )
    }

    private val user = User(id = "123").also {
        it.name = "Eduardo J."
        it.username = "Duduxs"
        it.email = "EduardoJ@gmail.com"
        it.password = "123"
        it.profile = employeeProfile
    }

    @BeforeAll
    fun init() {
        profiles.insert(employeeProfile)
        insertAdmin(profiles, users)
    }

    @Test
    fun `must execute CrudOperations`() {
        val userUpdated = user.copy().also {
            it.name = "Eduardo New Name"
            it.username = "Dudupp1"
            it.email = "EduardoJ@gmail.com"
            it.password = "123"
            it.password = "nowThePasswordIsStrong"
            it.profile = employeeProfile
        }

        val userList = (1..5).map { i ->
            User(id = i.toString()).also {
                it.name = "Nome $i"
                it.username = "Username $i"
                it.email = "email$i@gmail.com"
                it.password = "Senha $i"
                it.profile = employeeProfile
            }
        }

        withCrudOperations(
            entity = user,
            entities = userList,
            dto = UserDTO(user),
            newDataDTO = UserDTO(userUpdated),
            repository = users,
            assertFunction = user::assertEquals,
            assertSummaryFunction = user::assertSummaryEquals,
            assertUpdateFunction = userUpdated::assertEquals,
            assertListFunction = userList::assertCollectionEquals,
            assertListSummaryFunction = userList::assertCollectionSummaryEquals,
        )
    }

    @Test
    fun `must not insert user by duplicated data`() {
        val user = User().also { it.email = "sameEmail@gmail.com"; it.profile = employeeProfile }

        try {
            users.insert(user)
            Given {
                contentType(JSON)
                defaultAuthenticationHeader()
            } When {
                body(UserDTO(user))
                post()
            } Then {
                statusCode(409)
            }
        } finally {
            users.remove(user)
        }
    }
}