import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.annotations.QuarkusMain
import mu.KotlinLogging.logger
import mu.toKLogger

@QuarkusMain
object Main {

    val logger = logger("org.edudev.main").toKLogger()

    @JvmStatic
    fun main(args: Array<String>) {
        Quarkus.run(*args)
    }
}