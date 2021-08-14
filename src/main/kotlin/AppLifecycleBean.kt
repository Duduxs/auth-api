import Main.logger
import io.quarkus.runtime.StartupEvent
import org.edudev.arch.auth.HTTPVerb
import org.edudev.arch.extensions.isAnnotatedWithAnyThese
import org.edudev.arch.extensions.replaceLast
import org.edudev.utils.logotype
import org.reflections.Reflections
import javax.enterprise.context.ApplicationScoped

import javax.enterprise.event.Observes
import javax.ws.rs.Path

@ApplicationScoped
class AppLifecycleBean {

    fun onStart(@Observes ev: StartupEvent) {
        val resources = findResourcesInDomainPackage()

        val resourcesFormatted = resources.map { "|<${it.key}> Inicializado com rotas ${it.value}\n"}
            .toString()
            .replace(",", "")
            .replaceFirst("[", "")
            .replaceLast("]",  "")
            .trimMargin()

        logger.info {
            """ 
${"\n"}
${logotype()} 
=============================================================================================================================================
                                                * * * Aplicação inicializada com sucesso! * * *                                             
                                                                                                                                           
$resourcesFormatted
=============================================================================================================================================

        """.trimIndent()
        }
    }

    private fun findResourcesInDomainPackage() : HashMap<String, Collection<String>> {
        val resources = HashMap<String, Collection<String>>()

        val controllers = Reflections(DOMAIN_PACKAGES).getTypesAnnotatedWith(Path::class.java)

        controllers.forEach { controller ->
            resources[controller.simpleName] = controller.methods.filter { method -> method.isAnnotatedWithAnyThese(HTTPVerb.annotationHttpVerbs()) }.map { method -> method.name }
        }

        return resources
    }

    companion object {
        const val DOMAIN_PACKAGES = "org.edudev.domain"
    }
}