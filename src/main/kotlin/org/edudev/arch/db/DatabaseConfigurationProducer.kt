package org.edudev.arch.db

import com.mongodb.ConnectionString
import mu.KLogging
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
    @ConfigProperty(name = "MONGODB_URL") var url: String,
    @ConfigProperty(name = "MONGODB_USERNAME", defaultValue = "") var username: Optional<String>,
    @ConfigProperty(name = "MONGODB_PASSWORD", defaultValue = "") var password: Optional<String>
) {

    //TODO printar a url quando subir

    @Produces
    fun produce() = MongoConfig(
        url = ConnectionString(url),
        username = username.orElse(null),
        password = password.orElse(null)
    )
}