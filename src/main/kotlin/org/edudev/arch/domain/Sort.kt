package org.edudev.arch.domain

import java.io.Serializable
import javax.ws.rs.DefaultValue
import javax.ws.rs.QueryParam


class Sort(
    val field: String,
    val type: SortOrder = SortOrder.ASCENDING
) : Serializable

enum class SortOrder(val type: String, val abbr: String) {
    ASCENDING("ASCENDING", "ASC"),
    DESCENDING("DESCENDING", "DESC")
}