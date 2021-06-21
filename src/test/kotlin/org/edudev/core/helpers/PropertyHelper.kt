package org.edudev.core.helpers

import org.edudev.domain.properties.Property
import org.edudev.domain.properties.PropertyDTO
import org.edudev.domain.properties.PropertySummaryDTO
import org.edudev.domain.properties.directionalities.Directionality
import java.util.*
import javax.enterprise.context.Dependent

@Dependent
class PropertyHelper : GenericHelper<Property, PropertyDTO, PropertySummaryDTO> {

    fun createDomain(
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

    override fun createDTO(domain: Property) = PropertyDTO(
        createDomain(
            _id = domain._id,
            name = domain.name,
            address = domain.address,
            directionality = domain.directionality,
            value = domain.value
        )
    )

    override fun createSummaryDTO(domain: Property) = PropertySummaryDTO(
        createDomain(
            _id = domain._id,
            name = domain.name,
            value = domain.value
        )
    )
}


