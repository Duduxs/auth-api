package org.edudev.domain.users.profile

import org.edudev.arch.auth.Restricted
import org.edudev.arch.auth.functionality.GlobalFunctionality.EMPTY
import org.edudev.arch.auth.functionality.GlobalFunctionality.PROFILES
import org.edudev.arch.exceptions.BadRequestHttpException
import org.edudev.arch.services.CrudService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response

@Path("/profiles")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Restricted(PROFILES)
class ProfilesService @Inject constructor(
    profiles: Profiles,
    profileDTOMapper: ProfileDTOMapper
) : CrudService<Profile, ProfileDTO, ProfileDTO>(
    repository = profiles,
    mapper = profileDTOMapper
) {
    override fun save(dto: ProfileDTO): Response {
        if(dto.permissions.any { it.functionality === EMPTY }) throw BadRequestHttpException("Funcionalidade $EMPTY não é cadastrável.")
        return super.save(dto)
    }
}