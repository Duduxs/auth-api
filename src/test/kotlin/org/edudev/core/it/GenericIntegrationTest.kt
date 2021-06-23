package org.edudev.core.it

import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.repositories.Repository
import org.edudev.core.helpers.assertEquals
import org.edudev.domain.properties.PropertyDTO
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import javax.inject.Inject

@TestInstance(PER_CLASS)
open class GenericIntegrationTest<E : DomainEntity, DTO : Any, DTO_S>(
    private val entityClass: Class<E>,
    private val dtoClass: Class<DTO>,
    private val dto: DTO
) {

    @Inject
    lateinit var repository: Repository<E>

    private val entity: E = entityClass.getDeclaredConstructor().newInstance()

    @BeforeAll
    private fun init() {
        repository.insert(entity)
    }

    @Test
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
    fun `Must find the correct entity by id`() {
        Given {
            contentType(JSON)
        } When {
            get("/${entity._id}?summary=false")
        } Then {
            statusCode(200)
            val actual = repository.findById(entity._id)
                ?: throw NotFoundHttpException("Entidade com id ${entity._id} não encontrada!")

            val expected = extract().`as`(dtoClass)
            actual.assertEquals(dto = expected)
        }
    }

    @Test
    fun `Must not find the correct entity by id`() {
        Given {
            contentType(JSON)
        } When {
            get("/666?summary=false")
        } Then {
            statusCode(404)
        }
    }

//    @Test
//    fun `Must insert entity`() {
//        Given {
//            contentType(JSON)
//        } When {
//            body()
//            post()
//        } Then {
//            statusCode(200)
//            body(`is`(dtoClass))
//        }
//    }
//
//    @Test
//    fun `must fail on insert entity`() {
//        Given {
//            contentType(JSON)
//        } When {
//            body(PropertyDTO(Property()))
//            post()
//        } Then {
//            statusCode(200)
//        }
//    }

//    @Test
//    fun `Must list the entities`() {
//        Given {
//            contentType(JSON)
//        } When {
//            get("/666?summary=false")
//        } Then {
//            statusCode(404)
//        }
//    }


}