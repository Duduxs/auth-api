package org.edudev.domain.properties

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.it.GenericIntegrationTest
import org.edudev.core.persistence.MongoResource

@QuarkusTest
@TestHTTPEndpoint(PropertiesService::class)
@QuarkusTestResource(MongoResource::class)
class PropertyIntegrationTest: GenericIntegrationTest<Property, PropertyDTO, PropertySummaryDTO>(
    entityClass = Property::class.java,
    dtoClass = PropertyDTO::class.java
)