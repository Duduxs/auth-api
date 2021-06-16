package org.edudev.core.helpers

import org.assertj.core.api.Assertions.assertThat
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

fun createPropertyDTO(
    property: Property
) = PropertyDTO(
    createProperty(
        _id = property._id,
        name = property.name,
        address = property.address,
        directionality = property.directionality,
        value = property.value
    )
)


fun createPropertySummaryDTO(
    property: Property
) = PropertySummaryDTO(
    createProperty(
        _id = property._id,
        name = property.name,
        value = property.value
    )
)

fun Property.assertEquals(dto: PropertyDTO): Any = assertThat(dto)
        .usingRecursiveComparison()
        .isEqualTo(this)

fun Collection<Property>.assertEquals(properties: Collection<PropertyDTO>) : Any = assertThat(properties).usingRecursiveComparison().isEqualTo(this)



