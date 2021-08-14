package org.edudev.domain.properties.zipcodes.boundary

import org.edudev.domain.properties.zipcodes.Address

interface ViaZipCodeBoundary {

    fun getByZipCode(zipCode: String): Address?
}