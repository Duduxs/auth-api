package org.edudev.domain.users

import Main.logger
import io.quarkus.test.common.http.TestHTTPEndpoint
import org.edudev.arch.auth.functionality.GlobalFunctionality
import org.edudev.arch.auth.functionality.action.CrudAction
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.core.configs.QuarkusIntegrationTest
import org.edudev.core.it.*
import org.edudev.core.security.DefaultAuth.insertAdmin
import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.Profiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.inject.Inject


//
//@QuarkusTest
//@QuarkusTestResource(MongoResource::class)
//class UserIntegrationTest : CrudIntegrationTest<User, UserDTO, UserDTO>(
//    rootPath = "/users",
//    entity = user,
//    dto = UserDTO(user),
//)

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
            Permission(GlobalFunctionality.USERS, listOf(CrudAction.INSERT, CrudAction.READ)),
            Permission(
                GlobalFunctionality.PROFILES,
                listOf(CrudAction.INSERT, CrudAction.READ, CrudAction.DELETE, CrudAction.UPDATE)
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
    fun `must execute ChangeOperations`(){
        val newUser = user.copy()
        //CRIAR UMA NOVA VAR PRA PASSAR O USUÁRIO A SER COMPARADO (NOVO) PRO UPDATE
        withChangeOperations(
            entity = user,
            dto = UserDTO(newUser),
            repository = users,
            assertFunction = newUser::assertEquals,
        )
    }

    @Test
    fun `must execute ReadOperations`() {
        val userList = (1..5).map { i ->
            User(id = i.toString()).also {
                it.name = "Nome $i"
                it.username = "Username $i"
                it.email = "email$i@gmail.com"
                it.password = "Senha $i"
                it.profile = employeeProfile
            }
        }

        withReadOperations(
            entity = user,
            entities = userList,
            repository = users,
            assertFunction = user::assertEquals,
            assertListFunction = userList::assertCollectionEquals,
            assertSummaryFunction = user::assertSummaryEqualss,
            assertListSummaryFunction = userList::assertCollectionSummaryEquals
        )
    }

}