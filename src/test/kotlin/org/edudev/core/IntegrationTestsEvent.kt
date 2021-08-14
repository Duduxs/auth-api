package org.edudev.core

import Main.logger
import io.quarkus.test.junit.callback.QuarkusTestBeforeClassCallback
import org.edudev.utils.logotype
import org.junit.jupiter.api.Test

class IntegrationTestsEvent : QuarkusTestBeforeClassCallback {

    override fun beforeClass(testClass: Class<*>) {
        val testMethods = testClass.methods.filter { it.isAnnotationPresent(Test::class.java) }

        logger.warn {
            """ 
${"\n"}
${logotype()}                                                                                  
=============================================================================================================================================
|                                     * * * Executando [${testMethods.size}] testes na classe ${testClass.simpleName} * * *                                    |
=============================================================================================================================================
        """.trimIndent()
        }
    }

}