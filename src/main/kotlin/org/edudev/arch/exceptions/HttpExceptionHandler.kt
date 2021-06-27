package org.edudev.arch.exceptions

import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpResponseStatus.*
import mu.KLogging
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response

open class HttpExceptionHandler(status: HttpResponseStatus, payload: String) : Exception() {

    init {
        val response = Response
            .status(status.code())
            .entity("""
                |{
                |"Code": ${status.code()}
                |${"\t"}"Error": "$payload"
                |}
                |""".trimMargin())
            .type(APPLICATION_JSON)
            .build()

        logger.error { " * * * (HTTP: ${response.status} | ${response.statusInfo}) -> $payload * * * "}

       throw WebApplicationException(response)
    }

    companion object : KLogging()
}

class BadRequestHttpException(payload: String = "Requisição mal feita!") : HttpExceptionHandler(BAD_REQUEST, payload)
class NotFoundHttpException(payload: String = "Não encontrado!") : HttpExceptionHandler(NOT_FOUND, payload)
class ConflictHttpException(payload: String = "Conflito!") : HttpExceptionHandler(CONFLICT, payload)
class NotAcceptableHttpException(payload: String = "Não Aceito!") : HttpExceptionHandler(NOT_ACCEPTABLE, payload)

