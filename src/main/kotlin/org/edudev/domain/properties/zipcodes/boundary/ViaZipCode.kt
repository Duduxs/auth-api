package org.edudev.domain.properties.zipcodes.boundary

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ViaZipCode(
    @JsonProperty(value = "cep") val zipCode: String,
    @JsonProperty(value = "logradouro") val street: String,
    @JsonProperty(value = "bairro") val district: String,
    @JsonProperty(value = "localidade") val city: String,
    @JsonProperty(value = "complemento") val complement: String,
    @JsonProperty(value = "uf") val uf: String
)