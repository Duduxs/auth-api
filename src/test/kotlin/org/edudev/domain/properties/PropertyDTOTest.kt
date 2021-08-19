package org.edudev.domain.properties


import org.edudev.domain.properties.directionalities.Directionality
import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.properties.zipcodes.UF
import org.junit.jupiter.api.Test

class PropertyDTOTest {

    private val address = Address(
        zipCode = "53220375",
        district = "Peixinhos",
        street = "Av Vasco Rodriguês",
        city = "Olinda",
        uf = UF.PE
    )

    private val property = Property().also {
        it.name = "Property Domain"
        it.address = address
        it.directionality = Directionality.RENT
        it.value = 55000.0
    }

    @Test
    fun `PropertyDTO must be instantiable`() {
        val propertyDTO = PropertyDTO(property)
        property assertEquals propertyDTO
    }

    @Test
    fun `PropertySummaryDTO must be instantiable`() {
        val propertySummaryDTO = PropertySummaryDTO(property)
        property assertSummaryEquals propertySummaryDTO
    }

    @Test
    fun `Must update property in the update method`() {
        val propertyDTO = PropertyDTO(property)
        val newProperty = Property(propertyDTO.id)

        propertyDTO.update(newProperty) { userSearchMock(it) }
        newProperty assertEquals propertyDTO
    }

    private fun userSearchMock(id: String) = if (property.user?.id == id) this.property.user else null
}