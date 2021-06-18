package org.edudev

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/properties/size")
          .then()
             .statusCode(200)
             .body(`is`("3"))
    }


}