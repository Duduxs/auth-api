package org.edudev.domain.properties


import org.edudev.core.helper.assertEquals
import org.edudev.core.helper.assertSummaryEquals
import org.edudev.domain.properties.directionalities.Directionality
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class PropertyDTOTest {

    private val property = createProperty(
        name = "Property Domain",
        address = "Property Address",
        directionality = Directionality.RENT,
        value = 55000.0
    )

    private val propertyDTO = createPropertyDTO(property)

    private val propertySummaryDTO = createPropertySummaryDTO(property)

    @Test
    fun `PropertyDTO must be instantiable`() {
        property.assertEquals(propertyDTO)
    }

    @Test
    fun `PropertySummaryDTO must be instantiable`(){
        property.assertSummaryEquals(propertySummaryDTO)
    }

    @Test
    fun `Must update property in the update method`() {
        val newProperty = createProperty(propertyDTO._id)

        propertyDTO.update(newProperty)
        newProperty.assertEquals(propertyDTO)
    }

}