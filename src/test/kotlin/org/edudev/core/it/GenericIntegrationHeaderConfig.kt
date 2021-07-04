package org.edudev.core.it

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.eclipse.microprofile.config.ConfigProvider

class GenericIntegrationHeaderConfig(rootPath: String) {

    private val testPort: String = ConfigProvider
        .getConfig()
        .getValue("quarkus.http.test-port", String::class.java)


    val headerConfig: RequestSpecification = RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setBaseUri("http://localhost:$testPort${rootPath.putSlashIfDontHave()}")
        .build()


    private fun String.putSlashIfDontHave() = if (this.startsWith("/")) this else "/$this"
}