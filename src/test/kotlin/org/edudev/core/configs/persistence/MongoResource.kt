package org.edudev.core.configs.persistence

import Main.logger
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
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
}