package org.edudev.core.it

import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import mu.KLogging
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.repositories.Repository
import org.edudev.core.helpers.assertEquals
import org.edudev.core.helpers.setNewId
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.util.*
import javax.inject.Inject

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
open class GenericIntegrationTest<E : DomainEntity, DTO : Any, DTO_S>(
    private val entity: E,
    private val dto: DTO
) {
    @Inject
    lateinit var repository: Repository<E>

    @BeforeAll
    private fun init() {
        repository.insert(entity)
    }

    @Test
    @Order(1)
    fun `Must assert the exactly quantity of entities in the db`() {
        Given {
            contentType(JSON)
        } When {
            get("/size")
        } Then {
            statusCode(200)
            body(`is`(repository.size().toString()))
        }
    }

    @Test
    @Order(2)
    fun `Must not find the correct entity by id`() {
        Given {
            contentType(JSON)
        } When {
            get("/666?summary=false")
        } Then {
            statusCode(404)
        }
    }

    @Test
    @Order(3)
    fun `Must find the correct entity by id`() {
        Given {
            contentType(JSON)
        } When {
            get("${entity._id}?summary=false")
        } Then {
            statusCode(200)
            val expected = repository.findById(entity._id)
                ?: throw NotFoundHttpException("Entidade com id ${entity._id} não encontrada!")
            val actual = extract().`as`(dto::class.java)
            expected.assertEquals(dto = actual)
        }
    }

    @Test
    @Order(4)
    fun `Must not insert entity`() {
        Given {
            contentType(JSON)
        } When {
            body(dto)
            post()
        } Then {
            statusCode(409)
        }
    }

    @Test
    @Order(5)
    fun `Must update entity`() {
        Given {
            contentType(JSON)
        } When {
            body(dto)
            put(entity._id)
        } Then {
            statusCode(200)
            val expected = repository.findById(entity._id)
                ?: throw NotFoundHttpException("Entidade com id ${entity._id} não encontrada!")
            val actual = extract().`as`(dto::class.java)
            expected.assertEquals(dto = actual)
        }
    }

    @Test
    @Order(6)
    fun `Must insert entity`() {
        dto.setNewId(UUID.randomUUID().toString())

        Given {
            contentType(JSON)
        } When {
            body(dto)
            post()
        } Then {
            statusCode(200)
        }
    }

    @Test
    @Order(7)
    fun `Must not update entity by different id`() {
        Given {
            contentType(JSON)
        } When {
            body(dto)
            put(entity._id)
        } Then {
            statusCode(406)
        }
    }

    @Test
    @Order(8)
    fun `Must not update entity by not found id`() {
        val newId = UUID.randomUUID().toString()
        dto.setNewId(newId)

        Given {
            contentType(JSON)
        } When {
            body(dto)
            put(newId)
        } Then {
            statusCode(404)
        }
    }
}