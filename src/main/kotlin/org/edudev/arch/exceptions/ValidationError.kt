package org.edudev.arch.exceptions

import mu.KLogging
import javax.ws.rs.core.Response

data class ConstraintJsonError(
    val code: Int = Response.Status.BAD_REQUEST.statusCode,
    val error: String
) {
    init {
        logger.error { "* * * (Constraint: ${code}) ->  $error * * *" }
    }

    companion object : KLogging()
}

data class ValidationError(
    val classAffected: Class<out Any>,
    val propertyAffected: String,
    val invalidValue: Any,
    val errorMessage: String,
) {

    fun toConstraintJsonError() = ConstraintJsonError(
        error = "Violação na classe [${classAffected.simpleName}] ao processar a propriedade [$propertyAffected]." +
                " O valor passado foi [$invalidValue], entretanto [$errorMessage]"
    )
}
