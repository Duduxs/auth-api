package org.edudev.domain.properties

import org.edudev.arch.auth.Restricted
import org.edudev.arch.auth.functionality.GlobalFunctionality.PROFILES
import org.edudev.arch.auth.functionality.GlobalFunctionality.PROPERTIES
import org.edudev.arch.services.CrudService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("properties")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Restricted(PROPERTIES)
class PropertiesService @Inject constructor(
    repository: Properties,
    mapper: PropertyDTOMapper
) : CrudService<Property, PropertyDTO, PropertySummaryDTO>(
    repository = repository,
    mapper = mapper
)

