package org.edudev.domain.properties.zipcodes.boundary

import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.properties.zipcodes.UF
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.ws.rs.WebApplicationException
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder

class ViaCepBoundaryIntegrationTest {

    private val client: Client = ClientBuilder.newClient()

    private val viaZipCodeBoundary = ViaZipCodeBoundaryHttpImpl(client)

    private val expectedAddress = Address(
        zipCode = "53220375",
        street = "Avenida Vasco Rodrigues",
        district = "Peixinhos",
        city = "Olinda",
        uf = UF.PE
    )

    @Test
    fun `Must consult with success viaZipCode API`() {
        val actualAddress = viaZipCodeBoundary.getByZipCode("53220-375")
        assertNotNull(actualAddress)
        assertIt(actualAddress!!)
    }

    @Test
    fun `Must throw BadRequest by zipCode tipped incorrectly`(){
        val errorMsg = assertThrows(WebApplicationException::class.java) { viaZipCodeBoundary.getByZipCode("53220 375") }.message
        assertEquals(errorMsg, "HTTP 400 Bad Request")
    }

    private fun assertIt(actualAddress: Address) {
        assertEquals(expectedAddress.zipCode, actualAddress.zipCode)
        assertEquals(expectedAddress.street, actualAddress.street)
        assertEquals(expectedAddress.district, actualAddress.district)
        assertEquals(expectedAddress.city, actualAddress.city)
        assertEquals(expectedAddress.complement, actualAddress.complement)
        assertEquals(expectedAddress.uf, actualAddress.uf)

    }
}