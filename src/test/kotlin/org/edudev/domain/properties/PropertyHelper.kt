package org.edudev.domain.properties

import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.properties.zipcodes.AddressSummaryDTO
import org.junit.jupiter.api.Assertions.assertEquals

infix fun Property.assertEquals(dto: PropertyDTO){
    assertEquals(this.id, dto.id)
    assertEquals(this.name, dto.name)
    this.address!!.assertEquals(dto.address)
    assertEquals(this.directionality, dto.directionality)
    assertEquals(this.value, dto.value)
    assertEquals(this.user?.id, dto.user?.id)
}

infix fun Property.assertSummaryEquals(dto: PropertySummaryDTO){
    assertEquals(this.id, dto.id)
    assertEquals(this.name, dto.name)
    assertEquals(this.value, dto.value)
}

infix fun Address.assertEquals(address: Address){
    assertEquals(this.zipCode, address.zipCode)
    assertEquals(this.street, address.street)
    assertEquals(this.district, address.district)
    assertEquals(this.city, address.city)
    assertEquals(this.complement, address.complement)
    assertEquals(this.uf, address.uf)
}

infix fun Collection<Property>.assertCollectionEquals(dto: Collection<PropertyDTO>){
    val domains = this.sortedByDescending { it.id }
    val dtos = dto.sortedByDescending { it.id }

    domains.forEachIndexed { i, entity -> entity.assertEquals(dtos[i]) }
}

infix fun Collection<Property>.assertCollectionSummaryEquals(dto: Collection<PropertySummaryDTO>){
    val domains = this.sortedByDescending { it.id }
    val dtos = dto.sortedByDescending { it.id }

    domains.forEachIndexed { i, entity -> entity.assertSummaryEquals(dtos[i]) }
}
