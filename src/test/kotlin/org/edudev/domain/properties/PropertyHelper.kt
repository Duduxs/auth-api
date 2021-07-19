package org.edudev.domain.properties

import org.edudev.domain.properties.directionalities.Directionality
import java.util.*

val propertyWithPopulatedValues: Property = createProperty(
    id = UUID.randomUUID().toString(),
    name = "Propriedade de dominio",
    address = "Endereço de dominio",
    directionality = Directionality.SELL,
    value = 9.0
)

fun createProperty(
    id: String = UUID.randomUUID().toString(),
    name: String = "",
    address: String = "",
    directionality: Directionality = Directionality.RENT,
    value: Double = 0.0
) = Property(id = id).also {
    it.name = name
    it.address = address
    it.directionality = directionality
    it.value = value
}

fun createPropertyDTO(domain: Property) = PropertyDTO(domain)

fun createPropertySummaryDTO(domain: Property) = PropertySummaryDTO(domain)




