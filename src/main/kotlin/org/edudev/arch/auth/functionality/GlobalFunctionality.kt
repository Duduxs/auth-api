package org.edudev.arch.auth.functionality

enum class GlobalFunctionality(val functionality: String) {
    USERS("users"),
    PROPERTIES("properties"),
    PROFILES("profiles"),
    EMPTY("empty");

    companion object {
        fun from(functionality: String) = values().find { it.functionality == functionality }
    }
}
