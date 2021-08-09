package org.edudev.core.it

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.repositories.Repository
import org.edudev.core.configs.*
import org.edudev.domain.users.User
import org.edudev.domain.users.Users
import org.edudev.domain.users.profile.Profiles
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.condition.EnabledIf
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject
import kotlin.reflect.full.createInstance

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
open class ReadOnlyIntegrationTest<E : DomainEntity, DTO : Any, DTO_S>(
    private val rootPath: String,
    private val entity: E,
    private val dto: DTO,
    private val dto_s: DTO_S? = null
) {

    @Inject
    open lateinit var repository: Repository<E>

    open lateinit var config: CrudIntegrationHeaderConfig

    @Inject
    lateinit var users: Users

    @Inject
    lateinit var profile: Profiles

    private val entities: MutableCollection<E> = mutableListOf()

    @BeforeAll
    private fun init() {
        prepareAuthenticationHeader()
        mockEntitiesForListingTests()
    }

    private fun prepareAuthenticationHeader() {
        config = CrudIntegrationHeaderConfig(rootPath, users, profile)
        config.createDefaultAuthentication()
    }

    private fun mockEntitiesForListingTests() {
        Stream.generate { entity::class.createInstance() }
            .limit(3)
            .collect(Collectors.toList())
            .forEachIndexed { index, entity ->
                if(entity is User) {
                    entity.email = index.toString()
                    entity.username = index.toString()
                }
                entity.setNewId(index.toString())

                entities.add(entity)
                repository.insert(entity)
            }
    }

    @Test
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
    @Order(3)
    fun `Must find the correct entity by id`() {
        Given {
            spec(config.headerConfig)
        } When {
            get("${entity.id}?summary=false")
        } Then {
            statusCode(200)
            val expected = findEntityOrThrowNotFound(entity.id)
            val actual = extract().`as`(dto::class.java)
            expected.assertEquals(dto = actual)
        }
    }

    @Test
    @EnabledIf(
        value = "hasSummary",
        disabledReason = "Você precisa passar um dto summarizado se quiser executar esse teste!"
    )
    @Order(3)
    fun `Must find the correct entity summarized by id`() {
        Given {
            spec(config.headerConfig)
        } When {
            get(entity.id)
        } Then {
            statusCode(200)
            val expected = findEntityOrThrowNotFound(entity.id)
            val actual = extract().`as`(dto_s!!::class.java)

            expected.assertSummaryEquals(dto = actual)
        }
    }

    @Test
    fun `Must list entities from db`() {
        Given {
            spec(config.headerConfig)
        } When {
            get("?last=3&order=ASC&summary=false")
        } Then {
            statusCode(200)
            val values = extract().jsonPath().getList(".", dto.javaClass)

            entities.assertCollectionEquals(values)
        }
    }

    @Test
    @EnabledIf(
        value = "hasSummary",
        disabledReason = "Você precisa passar um dto summarizado se quiser executar esse teste!"
    )
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
    fun `Must list sorting id by ASC order`() {
        Given {
            spec(config.headerConfig)
        } When {
            get("?last=3&field=id&order=ASC&summary=false")
        } Then {
            statusCode(200)
            val values = extract().jsonPath().getList(".", dto.javaClass)

            entities.assertCollectionEquals(values)
        }
    }

    @Test
    fun `Must return only two entities in list sorting id by ASC order`() {
        Given {
            spec(config.headerConfig)
        } When {
            get("?last=2&field=id&order=ASC&summary=false")
        } Then {
            statusCode(200)
            val values = extract().jsonPath().getList(".", dto.javaClass)

            entities.filter { it.id.toInt() < 2 }.assertCollectionEquals(values)
        }
    }

    @Test
    fun `Must support all query params in list`() {
        Given {
            spec(config.headerConfig)
        } When {
            get("?first=0&last=9&q=entity&field=id&order=ASC&summary=false")
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun `Must support without any query params in list`() {
        Given {
            spec(config.headerConfig)
        } When {
            get()
        } Then {
            statusCode(200)
        }
    }

    private fun hasSummary() = (dto_s != null)

    protected fun findEntityOrThrowNotFound(id: String) =
        repository.findById(id) ?: throw NotFoundHttpException("Entidade com id ${entity.id} não encontrada!")
}