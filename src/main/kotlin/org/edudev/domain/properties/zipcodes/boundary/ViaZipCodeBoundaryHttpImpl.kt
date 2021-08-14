package org.edudev.domain.properties.zipcodes.boundary

import Main.logger
import org.edudev.arch.exceptions.BadRequestHttpException
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.exceptions.UnprocessableEntityHttpException
import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.properties.zipcodes.UF
import javax.enterprise.context.Dependent
import javax.ws.rs.BadRequestException
import javax.ws.rs.ProcessingException
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Dependent
class ViaZipCodeBoundaryHttpImpl(
    private val client: Client = ClientBuilder.newClient()
) : ViaZipCodeBoundary {

    override fun getByZipCode(zipCode: String): Address? {
        try {
            return client.target("$ZIPCODE_ROOT_PATH/$zipCode/json")
                .request(APPLICATION_JSON)
                .get(ViaZipCode::class.java)
                ?.let { newAddress(it) }
        } catch (e: Exception) {
            logger.error { e.message }
            when (e) {
                is BadRequestException -> throw BadRequestHttpException("CEP deve somente conter 8 digitos sem caracteres especiais ou espaços em branco")
                is ProcessingException -> throw NotFoundHttpException("CEP $zipCode não existe!")
                else -> throw UnprocessableEntityHttpException("Falha ao consultar API do ViaCEP")
            }
        }
    }

    private fun newAddress(viaZipCode: ViaZipCode) = Address(
        zipCode = viaZipCode.zipCode.replace("-", ""),
        street = viaZipCode.street,
        district = viaZipCode.district,
        city = viaZipCode.city,
        complement = viaZipCode.complement,
        uf = UF.valueOf(viaZipCode.uf)
    )


    companion object {
        const val ZIPCODE_ROOT_PATH = "http://viacep.com.br/ws"
    }


}