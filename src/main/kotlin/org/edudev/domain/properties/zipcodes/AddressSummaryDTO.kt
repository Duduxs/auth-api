package org.edudev.domain.properties.zipcodes

import org.edudev.arch.domain.DomainEntity
import java.util.*

data class AddressSummaryDTO(
    override val id: String = UUID.randomUUID().toString(),
    val street: String,
    val uf: UF?
) : DomainEntity {

    constructor(address: Address) : this(
        id = address.id,
        street = address.street,
        uf = address.uf

    )
}