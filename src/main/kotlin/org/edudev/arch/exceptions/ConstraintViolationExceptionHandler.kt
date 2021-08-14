package org.edudev.arch.exceptions


import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Path
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status.BAD_REQUEST
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
open class ConstraintViolationExceptionHandler : ExceptionMapper<ConstraintViolationException> {
    override fun toResponse(constraint: ConstraintViolationException): Response {
        val violations = constraint.constraintViolations.map { toValidationError(it).toConstraintJsonError() }

        return Response.status(BAD_REQUEST).entity(violations).type(APPLICATION_JSON).build()
    }

    private fun toValidationError(constraintViolation: ConstraintViolation<*>) = ValidationError(
        classAffected = constraintViolation.leafBean::class.java,
        propertyAffected = constraintViolation.propertyPath.propertyName(),
        invalidValue = constraintViolation.invalidValue,
        errorMessage = constraintViolation.message
    )

    private fun Path.propertyName() = this.toString().split(".").last()

}