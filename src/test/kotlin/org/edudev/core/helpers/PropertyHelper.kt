package org.edudev.core.helpers

import org.edudev.domain.properties.Property
import org.edudev.domain.properties.PropertyDTO
import org.edudev.domain.properties.PropertySummaryDTO
import org.edudev.domain.properties.directionalities.Directionality
import java.util.*


fun createProperty(
    _id: String = UUID.randomUUID().toString(),
    name: String = "",
    address: String = "",
    directionality: Directionality = Directionality.RENT,
    value: Double = 0.0
) = Property(_id = _id).also {
    it.name = name
    it.address = address
    it.directionality = directionality
    it.value = value
}

fun createPropertyDTO(domain: Property) = PropertyDTO(
    createProperty(
        _id = domain._id,
        name = domain.name,
        address = domain.address,
        directionality = domain.directionality,
        value = domain.value
    )
)

fun createPropertySummaryDTO(domain: Property) = PropertySummaryDTO(
    createProperty(
        _id = domain._id,
        name = domain.name,
        value = domain.value
    )
)


