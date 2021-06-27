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
import org.edudev.core.helpers.assertSummaryEquals
import org.edudev.core.helpers.setNewId
import org.edudev.domain.properties.PropertyDTO
import org.edudev.domain.properties.PropertySummaryDTO
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assume
import org.junit.Ignore
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject
import kotlin.reflect.full.createInstance

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
open class GenericIntegrationTest<E : DomainEntity>(
    private val entity: E,
    private val dto: Any,
    private val dto_s: Any? = null
) {
    @Inject
    lateinit var repository: Repository<E>

    private val entities: MutableCollection<E> = mutableListOf()

    @BeforeAll
    private fun init() {
        repository.insert(entity)
        mockEntitiesForListingTests()
    }

    private fun mockEntitiesForListingTests() {
        Stream.generate { entity::class.createInstance() }
            .limit(5)
            .collect(Collectors.toList())
            .forEachIndexed { index, entity ->
                entity.setNewId(index.toString())
                entities.add(entity)
                repository.insert(entity)
            }
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
            val expected = findEntityOrThrowNotFound(entity._id)
            val actual = extract().`as`(dto::class.java)

            expected.assertEquals(dto = actual)
        }
    }

    @Test
    @Order(3)
    fun `Must find the correct entity summarized by id`() {
        Assume.assumeTrue("Você precisa passar um dto summarizado se quiser executar esse teste!", dto_s != null)

        Given {
            contentType(JSON)
        } When {
            get(entity._id)
        } Then {
            statusCode(200)
            val expected = findEntityOrThrowNotFound(entity._id)
            val actual = extract().`as`(dto_s!!::class.java)

            expected.assertSummaryEquals(dto = actual)
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
            val expected = findEntityOrThrowNotFound(entity._id)
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
    fun `Must delete entity`() {
        Given {
            contentType(JSON)
        } When {
            delete(entity._id)
        } Then {
            statusCode(204)
        }
    }

    @Test
    @Order(9)
    fun `Must not delete entity by not found id`() {
        Given {
            contentType(JSON)
        } When {
            delete(entity._id)
        } Then {
            statusCode(404)
        }
    }

    @Test
    @Order(10)
    fun `Must not update entity by not found id`() {
        dto.setNewId(entity._id)

        Given {
            contentType(JSON)
        } When {
            body(dto)
            put(entity._id)
        } Then {
            statusCode(404)
        }
    }

    @Test
    @Order(11)
    fun `Must list summarized entities from db`() {
        Assume.assumeTrue("Você precisa passar um dto summarizado se quiser executar esse teste!", dto_s != null)

        Given {
            contentType(JSON)
        } When {
            get("?first=1&last=5")
        } Then {
            statusCode(200)
            val values = extract().jsonPath().getList(".", dto_s!!.javaClass)
            values.forEachIndexed { index, dto_s -> entities.elementAt(index).assertSummaryEquals(dto_s)  }
        }
    }

    companion object : KLogging()

    private fun findEntityOrThrowNotFound(id: String) =
        repository.findById(id) ?: throw NotFoundHttpException("Entidade com id ${entity._id} não encontrada!")
}