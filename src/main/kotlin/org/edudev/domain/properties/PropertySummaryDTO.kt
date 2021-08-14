package org.edudev.domain.properties

import org.edudev.domain.properties.zipcodes.AddressSummaryDTO
import java.util.*


data class PropertySummaryDTO(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val value: Double,
    val address: AddressSummaryDTO?
) {
    constructor(property: Property) : this(
        property.id,
        property.name,
        property.value,
        property.address?.let { AddressSummaryDTO(it) }
    )
}