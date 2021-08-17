package org.edudev.arch.domain

import javax.ws.rs.DefaultValue
import javax.ws.rs.QueryParam

@NoArg
class Page {

    @QueryParam("first")
    @DefaultValue("0")
    var first: Int = 0

    @QueryParam("last")
    @DefaultValue("10")
    var last: Int = 10

    constructor(first: Int = 0, last: Int = 10) {
        this.first = first
        this.last = last
    }
}