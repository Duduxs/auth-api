package org.edudev.domain.users

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.it.CrudIntegrationTest
import org.edudev.core.persistence.MongoResource

@QuarkusTest
@QuarkusTestResource(MongoResource::class)
class UserIntegrationTest : CrudIntegrationTest<User>(
    rootPath = "/users",
    entity = userWithPopulatedValues,
    dto = createUserDTO(userWithPopulatedValues)
)