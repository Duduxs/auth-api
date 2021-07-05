package org.edudev.domain.properties

import java.util.*


data class PropertySummaryDTO(
    val _id: String = UUID.randomUUID().toString(),
    val name: String,
    val value: Double
) {
    constructor(property: Property) : this(
        property._id,
        property.name,
        property.value
    )
}