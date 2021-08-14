package org.edudev.core.configs

import io.restassured.authentication.PreemptiveBasicAuthScheme
import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType.JSON
import io.restassured.specification.RequestSpecification
import org.eclipse.microprofile.config.ConfigProvider
import org.edudev.arch.auth.functionality.GlobalFunctionality.*
import org.edudev.arch.auth.functionality.action.CrudAction.*
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.domain.users.User
import org.edudev.domain.users.Users
import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.Profiles
import javax.inject.Inject

class CrudIntegrationHeaderConfig @Inject constructor(
    private val rootPath: String,
    private val users: Users,
    private val profiles: Profiles
) {

    private val testPort: String = ConfigProvider
        .getConfig()
        .getValue("quarkus.http.test-port", String::class.java)
        .orDefaultTestPort()

    private fun withDefaultURI() = "http://localhost:$testPort${rootPath.putSlashAtStartIfDontHave()}"

    private fun withDefaultAuthenticated() = PreemptiveBasicAuthScheme().apply { userName = "admin"; password = "123" }

    fun createDefaultAuthentication() {
        val adminPermissions: Collection<Permission> = listOf(
                Permission(USERS, listOf(INSERT, UPDATE, READ, DELETE)),
                Permission(PROFILES, listOf(INSERT, UPDATE, READ, DELETE)),
                Permission(PROPERTIES, listOf(INSERT, UPDATE, READ, DELETE))
        )

        val adminProfile = Profile().also {
            it.permissions = adminPermissions
        }

        profiles.insert(adminProfile)

        val admin = User().apply {
            username = "admin"
            password = "123"
            profile = adminProfile
        }
        users.insert(admin)
    }

    val headerConfig: RequestSpecification = RequestSpecBuilder()
        .setContentType(JSON)
        .setBaseUri(withDefaultURI())
        .setAuth(withDefaultAuthenticated())
        .build()
}

fun String?.orDefaultTestPort() = this ?: "8084"
fun String.putSlashAtStartIfDontHave() = if (this.startsWith("/")) this else "/$this"



