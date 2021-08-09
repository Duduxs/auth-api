package org.edudev.arch.auth.functionality.action

enum class CrudAction(val action: String) {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    READ("read");

    companion object {
        fun from(action: String) = values().find { it.action == action }
    }
}