package org.edudev.domain.properties.zipcodes

import dev.morphia.annotations.Entity
import javax.validation.constraints.Size

@Entity(value = "address", useDiscriminator = false)
data class Address(
    @field:Size(min = 8 , max = 8) var zipCode: String = "",
    var street: String = "",
    var district: String? = "",
    var city: String = "",
    var complement: String? = "",
    var uf: UF?,
)