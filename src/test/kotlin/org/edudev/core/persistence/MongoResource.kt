package org.edudev.core.persistence

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import mu.KLogging
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName

class MongoResource : QuarkusTestResourceLifecycleManager {

    private val container = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))

    override fun start(): MutableMap<String, String>{
        container.start()
        logger.info { "Inicializando mongo de testes em ${container.replicaSetUrl}" }
        return mutableMapOf("MONGODB_URL" to container.replicaSetUrl)
    }

    override fun stop() { container.stop() }

    companion object : KLogging()
}