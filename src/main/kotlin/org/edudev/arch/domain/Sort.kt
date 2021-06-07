package org.edudev.arch.domain

import java.io.Serializable

data class Sort(
    val field: String,
    val order: SortOrder = SortOrder.ASCENDING
) : Serializable

enum class SortOrder(val type: String) {
    ASCENDING("ASC"),
    DESCENDING("DESC")
}