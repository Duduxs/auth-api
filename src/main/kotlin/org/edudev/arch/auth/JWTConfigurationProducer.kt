package org.edudev.arch.auth

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces
import javax.inject.Inject

data class JWTSecret(
    val secret: String
)

@ApplicationScoped
class JWTConfigurationProducer @Inject constructor(
    @ConfigProperty(name = "JWT_SECRET_KEY") var jwtSecret: String,
) {

    @Produces
    fun produce() = JWTSecret(jwtSecret)

}