package org.edudev.domain.properties

import io.quarkus.test.common.http.TestHTTPEndpoint
import org.edudev.core.it.QuarkusIntegrationTest
import org.edudev.core.it.withChangeOperations
import org.edudev.core.it.withReadOperations
import org.edudev.core.security.DefaultAuth.insertAdmin
import org.edudev.domain.properties.directionalities.Directionality.RENT
import org.edudev.domain.properties.directionalities.Directionality.SELL
import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.properties.zipcodes.UF.PE
import org.edudev.domain.users.Users
import org.edudev.domain.users.profile.Profiles
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusIntegrationTest
@TestHTTPEndpoint(PropertiesService::class)
class PropertyIntegrationTest {

    @Inject
    private lateinit var profiles: Profiles

    @Inject
    private lateinit var users: Users

    @Inject
    private lateinit var properties: Properties

    private val address = Address(
        zipCode = "53220375",
        street = "Avenida Vasco Rodrigues",
        district = "Peixinhos",
        city = "Olinda",
        uf = PE
    )

    private val property = Property().also {
        it.name = "Condomíno da Pesqueira"
        it.address = address
        it.directionality = SELL
        it.value = 200000.00
    }

    @BeforeAll
    fun init() {
        insertAdmin(profiles, users)
    }

    @Test
    fun `must execute all ChangeOperations`() {
        val propertyUpdated = property.copy().also {
            it.name = "Condomínio Parque da Música"
            it.address = address
            it.directionality = RENT
            it.value = 450000.00
        }

        withChangeOperations(
            entity = property,
            repository = properties,
            dto = PropertyDTO(property),
            newDataDTO = PropertyDTO(propertyUpdated),
            assertFunction = property::assertEquals,
            assertUpdateFunction = propertyUpdated::assertEquals
        )
    }

    @Test
    fun `must execute all ReadOperations`(){
        val propertiesList = (1..5).map { i ->
            Property(id = i.toString()).also {
                it.name = "Nome $i"
                it.address = address
                it.directionality = RENT
                it.value = i.toDouble()
            }
        }

        withReadOperations(
            entity = property,
            entities = propertiesList,
            repository = properties,
            assertFunction = property::assertEquals,
            assertSummaryFunction = property::assertSummaryEquals,
            assertListFunction = propertiesList::assertCollectionEquals,
            assertListSummaryFunction = propertiesList::assertCollectionSummaryEquals
        )

    }
}