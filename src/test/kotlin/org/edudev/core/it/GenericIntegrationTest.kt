package org.edudev.core.it

import io.quarkus.test.common.QuarkusTestResource
import io.restassured.http.ContentType
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.repositories.Repository
import org.edudev.core.helpers.GenericHelper
import org.edudev.core.persistence.MongoResource
import org.edudev.domain.properties.Property
import org.edudev.domain.properties.PropertyDTO
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import javax.inject.Inject

@TestInstance(PER_CLASS)
open class GenericIntegrationTest<E : DomainEntity, DTO : Any, DTO_S>(
    private val entityClass: Class<E>,
    private val dtoClass: Class<DTO>
) {
    @Inject
    lateinit var helper: GenericHelper<E, DTO, DTO_S>

    @Inject
    lateinit var repository: Repository<E>

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
    fun `must fail on insert breeder`() {
        Given {
            contentType(JSON)

        } When {
            body(PropertyDTO(Property()))
            post()
        } Then {
            statusCode(200)
        }
    }

    @Test
    fun `Must find the correct entity in the findById method`() {
        Given {
            contentType(JSON)
        } When {
            get("/4ec2055e-4696-476e-aea2-9f930cd40295?summary=false")
        } Then {
            statusCode(200)
            val actual = repository.findById("4ec2055e-4696-476e-aea2-9f930cd40295")
                ?: throw NotFoundHttpException("Entidade com id 4ec2055e-4696-476e-aea2-9f930cd40295 não encontrada!")

            val expected = extract().`as`(dtoClass)
            helper.assertEquals(entity = actual, dto = expected)
        }
    }


}