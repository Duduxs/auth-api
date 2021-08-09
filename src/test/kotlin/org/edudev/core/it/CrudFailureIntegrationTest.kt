package org.edudev.core.it

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.edudev.core.configs.CrudIntegrationHeaderConfig
import org.edudev.core.configs.persistence.MongoResource
import org.edudev.domain.users.User
import org.edudev.domain.users.UserDTO
import org.edudev.domain.users.Users
import org.edudev.domain.users.profile.Profiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@QuarkusTestResource(MongoResource::class)
class CrudFailureIntegrationTest {

    @Inject
    lateinit var users: Users

    @Inject
    lateinit var profiles: Profiles

    lateinit var config: CrudIntegrationHeaderConfig

    private val user = User(id = "1").apply {
        email = "duduxss3@gmail.com"
        username = "Eduardo J"
        password = "123"
    }

    private val dto = UserDTO(user)

    @BeforeAll
    private fun init() {
        prepareAuthenticationHeader()
        insertEntity()
    }

    private fun prepareAuthenticationHeader() {
        config = CrudIntegrationHeaderConfig(USERS_ROOT_PATH, users, profiles)
        config.createDefaultAuthentication()
    }

    private fun insertEntity() {
        users.insert(user)
    }

    @Test
    fun `Must not find the correct entity by id`() {
        Given {
            spec(config.headerConfig)
        } When {
            get("/123?summary=false")
        } Then {
            statusCode(404)
        }
    }

    @Test
    fun `Must not insert entity by conflict data`() {
        val entityWithExistingEmail = UserDTO(User().apply {
            email = "duduxss3@gmail.com"
        })

        Given {
            spec(config.headerConfig)
        } When {
            body(entityWithExistingEmail)
            post()
        } Then {
            statusCode(409)
        }
    }

    @Test
    fun `Must not delete entity by not found id`() {
        Given {
            spec(config.headerConfig)
        } When {
            delete("/123")
        } Then {
            statusCode(404)
        }
    }

    @Test
    fun `Must not update entity by different id`() {
        val dtoWithDifferentId = User(id = "2")

        Given {
            spec(config.headerConfig)
        } When {
            body(dto)
            put(dtoWithDifferentId.id)
        } Then {
            statusCode(406)
        }
    }

    @Test
    fun `Must not update entity by not found id`() {
        val dtoWithNotFindableId = UserDTO(User(id = "2"))

        Given {
            spec(config.headerConfig)
        } When {
            body(dtoWithNotFindableId)
            put(dtoWithNotFindableId.id)
        } Then {
            statusCode(404)
        }
    }

    @RepeatedTest(3)
    fun `Must not list entities by wrong query params`(repetitionInfo: RepetitionInfo) {
        val expression = when (repetitionInfo.currentRepetition) {
            1 -> "?first=-1&last=5"
            2 -> "?first=1&last=-3"
            else -> "?first=3&last=2"
        }
        Given {
            spec(config.headerConfig)
        } When {
            get(expression)
        } Then {
            statusCode(400)
        }
    }

    companion object {
        private const val USERS_ROOT_PATH = "/users"
    }

}