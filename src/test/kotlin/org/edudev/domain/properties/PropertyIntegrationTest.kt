package org.edudev.domain.properties

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.configs.persistence.MongoResource
import org.edudev.core.it.CrudIntegrationTest
import org.edudev.domain.properties.directionalities.Directionality.SELL
import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.properties.zipcodes.UF.PE

private val property = Property().apply {
        name = "Condomíno da Pesqueira"
        address = Address(zipCode = "53220375", street = "Vasco Rodriguês", uf = PE)
        directionality = SELL
        value = 200000.00
}

@QuarkusTest
@QuarkusTestResource(MongoResource::class)
class PropertyIntegrationTest : CrudIntegrationTest<Property, PropertyDTO, PropertySummaryDTO>(
    rootPath = "properties",
    entity = property,
    dto = PropertyDTO(property),
    dto_s = PropertySummaryDTO(property)
)
