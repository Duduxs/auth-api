package org.edudev.domain.users

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.it.GenericIntegrationTest
import org.edudev.core.persistence.MongoResource

@QuarkusTest
@QuarkusTestResource(MongoResource::class)
class UserIntegrationTest : GenericIntegrationTest<User>(
    rootPath = "/users",
    entity = userWithPopulatedValues,
    dto = createUserDTO(userWithPopulatedValues)
)