package org.edudev.arch.domain

import javax.ws.rs.DefaultValue
import javax.ws.rs.QueryParam


@NoArg
class Sort {

    @QueryParam("field")
    @DefaultValue("id")
    var field: String = "id"

    @QueryParam("order")
    @DefaultValue("DESC")
    var type: SortOrder = SortOrder.DESC

    constructor(field: String = "id", type: SortOrder = SortOrder.DESC) {
        this.field = field
        this.type = type
    }
}

enum class SortOrder(val type: String) {
    ASC("ASC"),
    DESC("DESC")
}