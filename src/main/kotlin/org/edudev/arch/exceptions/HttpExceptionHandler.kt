package org.edudev.arch.exceptions

import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND
import mu.KLogging
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType.TEXT_PLAIN
import javax.ws.rs.core.Response

open class HttpExceptionHandler(status: HttpResponseStatus, payload: String) : Exception() {

    init {
        val response = Response
            .status(status.code())
            .entity("""
                |Code: ${status.code()}
                |Error: $payload
                |""".trimMargin())
            .type(TEXT_PLAIN)
            .build()

        logger.error { " * * * (HTTP: ${response.status} | ${response.statusInfo}) -> $payload * * * "}

       throw WebApplicationException(response)
    }

    companion object : KLogging()
}

class NotFoundHttpException(payload: String = "Não encontrado!") : HttpExceptionHandler(NOT_FOUND, payload)

