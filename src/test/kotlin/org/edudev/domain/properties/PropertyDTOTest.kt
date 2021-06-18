package org.edudev.domain.properties


import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions.assertThat
import org.edudev.core.helpers.assertEquals
import org.edudev.core.helpers.createProperty
import org.edudev.core.helpers.createPropertyDTO
import org.edudev.domain.properties.directionalities.Directionality
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import javax.inject.Inject

@TestInstance(PER_CLASS)
class PropertyDTOTest {
//    given()
//    .`when`()["/hello"]
//    .then()
//    .statusCode(200)
//    .contentType(MediaType.APPLICATION_JSON)
//    .body(JsonMatcher.jsonEqualTo(expectedJson))

    private val property = createProperty(
        name = "Property Domain",
        address = "Property Address",
        directionality = Directionality.RENT,
        value = 55000.0
    )

    private val propertyDTO = createPropertyDTO(property)

    @Test
    fun `Property must be instantiable`() {
        property.assertEquals(propertyDTO)
    }

    @Test
    fun `Must update property in the update method`() {
        val newProperty = Property(propertyDTO._id)

        propertyDTO.update(newProperty)
        newProperty.assertEquals(propertyDTO)
    }


}