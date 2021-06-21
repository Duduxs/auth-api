package org.edudev.domain.properties


import org.edudev.core.helpers.PropertyHelper
import org.edudev.domain.properties.directionalities.Directionality
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class PropertyDTOTest {

    private val helper = PropertyHelper()

    private val property = helper.createDomain(
        name = "Property Domain",
        address = "Property Address",
        directionality = Directionality.RENT,
        value = 55000.0
    )

    private val propertyDTO = helper.createDTO(property)

    @Test
    fun `Property must be instantiable`() {
        helper.assertEquals(property, propertyDTO)
    }

    @Test
    fun `Must update property in the update method`() {
        val newProperty = helper.createDomain(propertyDTO._id)

        propertyDTO.update(newProperty)
        helper.assertEquals(newProperty, propertyDTO)
    }

}