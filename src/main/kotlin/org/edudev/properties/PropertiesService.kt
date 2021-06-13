package org.edudev.properties

import mu.KLogging
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.extensions.mappedWith
import org.edudev.arch.repositories.CrudRepositoryService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path( "properties")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class PropertiesService @Inject constructor(
    private val repository: Properties,
    private val mapper: PropertyDTOMapper
) : CrudRepositoryService<Property, PropertyDTO, PropertySummaryDTO>{

    override fun count() = repository.size()

    override fun findById(id: String, summary: Boolean): Any? {
        val entity = repository.findById(id) ?: throw NotFoundHttpException("A entitidade com id $id não existe!")
        return entity.mappedWith(mapper, summary)
    }

    override fun save(dto: PropertyDTO): PropertyDTO {
        val entity = mapper.unmap(dto)
        repository.insert(entity)
        return mapper.mapFull(entity)
    }

    companion object : KLogging()
}