package org.edudev.core.it

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import mu.KLogging
import org.edudev.arch.domain.DomainEntity
import org.edudev.core.configs.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder


@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
open class CrudIntegrationTest<E : DomainEntity, DTO : Any, DTO_S>(
    rootPath: String,
    private val entity: E,
    private val dto: DTO,
    dto_s: DTO_S? = null,
) : ReadOnlyIntegrationTest<E, DTO, DTO_S>(
    rootPath = rootPath,
    entity = entity,
    dto = dto,
    dto_s = dto_s,
) {

    @Test
    @Order(1)
    fun `Must insert entity`() {
        Given {
            spec(config.headerConfig)
        } When {
            body(dto)
            post()
        } Then {
            statusCode(201)
        }
    }

    @Test
    @Order(2)
    fun `Must update entity`() {
        Given {
            spec(config.headerConfig)
        } When {
            body(dto)
            put(entity.id)
        } Then {
            statusCode(200)
            val expected = findEntityOrThrowNotFound(entity.id)
            val actual = extract().`as`(dto::class.java)

            expected.assertEquals(dto = actual)
        }
    }

    @Test
    @Order(4)
    fun `Must delete entity`() {
        Given {
            spec(config.headerConfig)
        } When {
            delete(entity.id)
        } Then {
            statusCode(204)
        }
    }

}