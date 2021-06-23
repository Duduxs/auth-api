package org.edudev.domain.properties

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.helpers.createProperty
import org.edudev.core.helpers.createPropertyDTO
import org.edudev.core.it.GenericIntegrationTest
import org.edudev.core.persistence.MongoResource
import org.edudev.domain.properties.directionalities.Directionality

@QuarkusTest
@TestHTTPEndpoint(PropertiesService::class)
@QuarkusTestResource(MongoResource::class)
class PropertyIntegrationTest : GenericIntegrationTest<Property, PropertyDTO, PropertySummaryDTO>(
    entityClass = Property::class.java,
    dtoClass = PropertyDTO::class.java,
    dto = createPropertyDTO(
        createProperty(
            name = "Propriedade de teste",
            address = "Endereço de teste",
            directionality = Directionality.RENT,
            value = 5.0
        )
    )
)