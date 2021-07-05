package org.edudev.domain.properties

import org.edudev.arch.services.CrudRepositoryService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("properties")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class PropertiesService @Inject constructor(
    repository: Properties,
    mapper: PropertyDTOMapper
) : CrudRepositoryService<Property, PropertyDTO, PropertySummaryDTO>(
    repository = repository,
    mapper = mapper
)

