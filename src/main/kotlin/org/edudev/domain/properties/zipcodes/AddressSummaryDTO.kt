package org.edudev.domain.properties.zipcodes

data class AddressSummaryDTO(
    val street: String,
    val uf: UF?
)  {

    constructor(address: Address) : this(
        street = address.street,
        uf = address.uf

    )
}