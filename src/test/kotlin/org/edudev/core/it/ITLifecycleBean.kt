package org.edudev.core.it

import Main.logger
import io.quarkus.test.junit.callback.QuarkusTestBeforeClassCallback
import org.edudev.utils.logotype

class ITLifecycleBean : QuarkusTestBeforeClassCallback {

    override fun beforeClass(testClass: Class<*>) {
        logger.warn {
            """ 
${"\n"}
${logotype()}                                                                                  
=============================================================================================================================================
|                                             * * * Executando testes da classe ${testClass.simpleName} * * *                                   |
=============================================================================================================================================
        """.trimIndent()
        }
    }

}