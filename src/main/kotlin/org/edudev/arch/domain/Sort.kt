package org.edudev.arch.domain

import java.io.Serializable


class Sort(
    val field: String,
    val type: SortOrder
) : Serializable

enum class SortOrder(val type: String) {
    ASCENDING("ASCENDING"),
    DESCENDING("DESCENDING")
}