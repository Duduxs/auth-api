package org.edudev.domain.users

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.it.GenericIntegrationTest
import org.edudev.core.persistence.MongoResource

@QuarkusTest
@TestHTTPEndpoint(UsersService::class)
@QuarkusTestResource(MongoResource::class)
class UserIntegrationTest : GenericIntegrationTest<User>(
    entity = userWithPopulatedValues,
    dto = createUserDTO(userWithPopulatedValues)
)