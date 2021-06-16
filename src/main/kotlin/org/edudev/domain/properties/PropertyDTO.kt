package org.edudev.domain.properties

import org.edudev.arch.domain.NoArg
import org.edudev.domain.properties.directionalities.Directionality
import java.util.*


@NoArg
data class PropertyDTO(
    val _id: String = UUID.randomUUID().toString(),
    val name: String,
    val address: String,
    val directionality: Directionality,
    val value: Double
) {

    constructor(property: Property) : this(
        property._id,
        property.name,
        property.address,
        property.directionality,
        property.value
    )

    fun update(property: Property) = property.also {
        require(it._id == _id) { "Incompatible Id!" }
        it.name = name
        it.address = address
        it.directionality = directionality
        it.value = value
    }
}