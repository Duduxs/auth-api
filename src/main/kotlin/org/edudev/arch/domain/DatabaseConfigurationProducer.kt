package org.edudev.arch.domain

import com.mongodb.ConnectionString
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject

data class MongoConfig(
    val url: ConnectionString,
    val username: String?,
    val password: String?
)

@ApplicationScoped
class DatabaseConfigurationProducer @Inject constructor(
    @ConfigProperty(name = "MONGODB_URL ", defaultValue = "mongodb://localhost:27017/auth-api") var url: String,
    @ConfigProperty(name = "MONGODB_USERNAME", defaultValue = "") var username: Optional<String>,
    @ConfigProperty(name = "MONGODB_PASSWORD", defaultValue = "") var password: Optional<String>
) {
    @Produces
    fun produce() = MongoConfig(
        url = ConnectionString(url),
        username = username.orElse(null),
        password = password.orElse(null)
    )
}