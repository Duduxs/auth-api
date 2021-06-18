package org.edudev.core

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.repositories.Repository
import org.edudev.domain.properties.Properties
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.*
import javax.inject.Inject

open class GenericIntegrationTester<E: DomainEntity, DTO: Any, DTO_S>(
//    repository: Repository<E>
) {

    @Inject
    lateinit var repository: Properties

    @Test
    fun hallo(){

        Assertions.assertThat(repository.size()).isEqualTo(3L)
    }

    @Test
    fun `GET (size) - Must assert the exactly quantity of entities in the db`() {

        Given {
            contentType(JSON)
        } When {
            get("/size")
        } Then {
            statusCode(200)
            body(`is`("3"))
        }

//        val expectedJson = """{ "greeting": "Hello from MOCKED main service" }"""

    }
}