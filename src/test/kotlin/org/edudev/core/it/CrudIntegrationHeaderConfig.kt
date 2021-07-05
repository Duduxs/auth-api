package org.edudev.core.it

import io.restassured.authentication.PreemptiveBasicAuthScheme
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.eclipse.microprofile.config.ConfigProvider
import org.edudev.core.helper.orDefaultTestPort
import org.edudev.core.helper.putSlashAtStartIfDontHave

class CrudIntegrationHeaderConfig(
    rootPath: String,
) {

    private val authorization = PreemptiveBasicAuthScheme().also {
        it.userName = "Duduxs"
        it.password = "123"
    }

    private val testPort: String = ConfigProvider
        .getConfig()
        .getValue("quarkus.http.test-port", String::class.java)
        .orDefaultTestPort()

    private val uri: String = "http://localhost:$testPort${rootPath.putSlashAtStartIfDontHave()}"

    val headerConfig: RequestSpecification = RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .setAuth(authorization)
        .setBaseUri(uri)
        .build()
}


