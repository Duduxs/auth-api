package org.edudev.domain.properties

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.it.GenericIntegrationTest
import org.edudev.core.persistence.MongoResource

@QuarkusTest
@QuarkusTestResource(MongoResource::class)
class PropertyIntegrationTest : GenericIntegrationTest<Property>(
    rootPath = "properties",
    entity = propertyWithPopulatedValues,
    dto = createPropertyDTO(propertyWithPopulatedValues),
    dto_s = createPropertySummaryDTO(propertyWithPopulatedValues)
)