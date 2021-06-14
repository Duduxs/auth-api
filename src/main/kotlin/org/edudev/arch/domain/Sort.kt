package org.edudev.arch.domain

import java.io.Serializable
import javax.ws.rs.DefaultValue
import javax.ws.rs.QueryParam

@NoArg
data class Sort(
    @QueryParam("field")
    @DefaultValue("")
    val field: String = "",
    @QueryParam("order")
    @DefaultValue("ASCENDING")
    val order: SortOrder = SortOrder.ASCENDING
) : Serializable

enum class SortOrder(val type: String) {
    ASCENDING("ASCENDING"),
    DESCENDING("DESCENDING")
}