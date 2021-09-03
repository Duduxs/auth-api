package org.edudev.arch.auth

enum class AuthScheme(val scheme: String) {
    BASIC("Basic"),
    JWT("Jwt"),
}