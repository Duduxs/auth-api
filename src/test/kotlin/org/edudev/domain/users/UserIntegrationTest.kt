package org.edudev.domain.users

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.it.CrudIntegrationTest
import org.edudev.core.configs.persistence.MongoResource
import org.edudev.domain.users.profile.Profile

private val user = User().apply {
        username = "Eduardo J"
        email = "EduardoJ@gmail.com"
        password = "123"
        profile = Profile().also { it.name = "Basic" }
}

@QuarkusTest
@QuarkusTestResource(MongoResource::class)
class UserIntegrationTest : CrudIntegrationTest<User, UserDTO, UserDTO>(
    rootPath = "/users",
    entity = user,
    dto = UserDTO(user),
)
