package org.edudev.core.it

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import mu.KLogging
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.repositories.Repository
import org.edudev.core.helper.assertCollectionEquals
import org.edudev.core.helper.assertEquals
import org.edudev.core.helper.assertSummaryEquals
import org.edudev.core.helper.setNewId
import org.edudev.domain.users.Users
import org.edudev.domain.users.createAdminUser
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.condition.EnabledIf
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject
import kotlin.reflect.full.createInstance


@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
open class CrudIntegrationTest<E : DomainEntity>(
    rootPath: String,
    private val entity: E,
    private val dto: Any,
    private val dto_s: Any? = null,
)  {

    @Inject
    lateinit var repository: Repository<E>

    @Inject
    lateinit var users: Users

    private val entities: MutableCollection<E> = mutableListOf()

    private val config = CrudIntegrationHeaderConfig(rootPath)

    @BeforeAll
    private fun init() {
        repository.insert(entity)
        users.insert(createAdminUser())
        mockEntitiesForListingTests()
    }

    companion object : KLogging()

    private fun hasSummary(): Boolean = dto_s != null

    private fun mockEntitiesForListingTests() {
        Stream.generate { entity::class.createInstance() }
            .limit(4)
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
            spec(config.headerConfig)
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
            spec(config.headerConfig)
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
            spec(config.headerConfig)
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
    @EnabledIf(value = "hasSummary", disabledReason = "Você precisa passar um dto summarizado se quiser executar esse teste!")
    fun `Must find the correct entity summarized by id`() {
        Given {
             spec(config.headerConfig)
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
             spec(config.headerConfig)
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
             spec(config.headerConfig)
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
        dto.setNewId("1000")

        Given {
             spec(config.headerConfig)
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
             spec(config.headerConfig)
        } When {
            body(dto)
            put(entity._id)
        } Then {
            statusCode(406)
        }
    }

    @RepeatedTest(2)
    @Order(8)
    fun `Must delete entities`(repetitionInfo: RepetitionInfo) {
        val id = when (repetitionInfo.currentRepetition) {
            1 -> entity._id
            else -> "1000"
        }

        Given {
             spec(config.headerConfig)
        } When {
            delete(id)
        } Then {
            statusCode(204)
        }
    }

    @Test
    @Order(9)
    fun `Must not delete entity by not found id`() {
        Given {
             spec(config.headerConfig)
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
             spec(config.headerConfig)
        } When {
            body(dto)
            put(entity._id)
        } Then {
            statusCode(404)
        }
    }

    @RepeatedTest(3)
    @Order(11)
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

    @Test
    @Order(11)
    fun `Must support all query params in list`() {
        Given {
             spec(config.headerConfig)
        } When {
            get("?first=0&last=9&q=entity&field=_id&order=ASC&summary=false")
        } Then {
            statusCode(200)
        }
    }

    @Test
    @Order(11)
    fun `Must support without any query params in list`() {
        Given {
             spec(config.headerConfig)
        } When {
            get()
        } Then {
            statusCode(200)
        }
    }

    @Test
    @Order(11)
    fun `Must list entities from db`() {
        Given {
             spec(config.headerConfig)
        } When {
            get("?order=ASC&last=4&summary=false")
        } Then {
            statusCode(200)
            val values = extract().jsonPath().getList(".", dto.javaClass)

            entities.assertCollectionEquals(values)
        }
    }

    @Test
    @Order(11)
    @EnabledIf(value = "hasSummary", disabledReason = "Você precisa passar um dto summarizado se quiser executar esse teste!")
    fun `Must list summarized entities from db`() {
        Given {
             spec(config.headerConfig)
        } When {
            get("?order=ASC")
        } Then {
            statusCode(200)
            val values = extract().jsonPath().getList(".", dto_s!!.javaClass)

            values.forEachIndexed { index, dto_s -> entities.elementAt(index).assertSummaryEquals(dto_s) }
        }
    }

    @Test
    @Order(11)
    fun `Must return only two entities in list sorting id by ASC order`() {
        Given {
             spec(config.headerConfig)
        } When {
            get("?last=2&field=_id&order=ASC&summary=false")
        } Then {
            statusCode(200)
            val values = extract().jsonPath().getList(".", dto.javaClass)

            entities.filter { it._id.toInt() < 2 }.assertCollectionEquals(values)
        }
    }

    private fun findEntityOrThrowNotFound(id: String) =
        repository.findById(id) ?: throw NotFoundHttpException("Entidade com id ${entity._id} não encontrada!")
}