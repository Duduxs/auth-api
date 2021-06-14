package org.edudev.properties

import mu.KLogging
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.exceptions.NotAcceptableHttpException
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.extensions.mappedWith
import org.edudev.arch.repositories.CrudRepositoryService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("properties")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class PropertiesService @Inject constructor(
    private val repository: Properties,
    private val mapper: PropertyDTOMapper
) : CrudRepositoryService<Property, PropertyDTO, PropertySummaryDTO> {

    override fun count() = repository.size()

    override fun list(query: String, sort: List<Sort>, first: Long, last: Long, summary: Boolean): Collection<Any?> {
        return repository.list(
            query = query,
            sort = sort,
            page = Page(first, last)
        ).mappedWith(mapper = mapper, summary = summary)
    }

    override fun findById(id: String, summary: Boolean): Any? {
        val entity = basePropertyFromPath(id)
        return entity.mappedWith(mapper, summary)
    }

    override fun save(dto: PropertyDTO): PropertyDTO {
        val entity = mapper.unmap(dto)
        repository.insert(entity)
        return mapper.mapFull(entity)
    }

    override fun update(id: String, dto: PropertyDTO): PropertyDTO {
        val entity = mapper.unmap(dto)

        when {
            id != entity._id -> throw NotAcceptableHttpException("Uri ID [$id] e Body ID [${entity._id}] incompatíveis!")
            !repository.exists(id) -> throw NotFoundHttpException("A entitidade com id $id não existe!")
        }

        repository.update(entity)
        return mapper.mapFull(entity)
    }

    override fun delete(id: String) {
        val entity = basePropertyFromPath(id)
        repository.remove(entity)
    }


    fun basePropertyFromPath(id: String) =
        repository.findById(id) ?: throw NotFoundHttpException("Entidade com id $id não encontrada!")


}

