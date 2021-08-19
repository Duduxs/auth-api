package org.edudev.core.it
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import org.edudev.core.configs.persistence.MongoResource
import javax.enterprise.inject.Stereotype
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@QuarkusTest
@Stereotype
@QuarkusTestResource(MongoResource::class)
@Retention(RUNTIME)
@Target(CLASS)
annotation class QuarkusIntegrationTest