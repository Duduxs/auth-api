package org.edudev.domain.properties

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.GenericIntegrationTester
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@QuarkusTest
@TestHTTPEndpoint(PropertiesService::class)
@TestInstance(PER_CLASS)
class PropertyIntegrationTest: GenericIntegrationTester<Property, PropertyDTO, PropertySummaryDTO>()