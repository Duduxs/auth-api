package org.edudev.arch.auth

enum class HTTPVerb(val verb: String) {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH"),
    OPTIONS("OPTIONS"),
    HEAD("HEAD");

    companion object {
        fun from(verb: String) = values().find { it.verb == verb }
        fun annotationHttpVerbs() = listOf(javax.ws.rs.GET::class, javax.ws.rs.POST::class, javax.ws.rs.PUT::class, javax.ws.rs.DELETE::class, javax.ws.rs.PATCH::class, javax.ws.rs.OPTIONS::class, javax.ws.rs.HEAD::class)
    }
}
