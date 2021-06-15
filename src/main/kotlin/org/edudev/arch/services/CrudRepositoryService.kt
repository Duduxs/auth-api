package org.edudev.arch.services

import org.edudev.arch.domain.*
import org.edudev.arch.dtos.EntityDTOMapper
import org.edudev.arch.exceptions.NotAcceptableHttpException
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.extensions.mappedWith
import org.edudev.arch.repositories.Repository
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.*

@NoArg
open class CrudRepositoryService<T : DomainEntity, DTO : Any, DTO_S>(
    private val repository: Repository<T>,
    private val mapper: EntityDTOMapper<T, DTO, DTO_S>
) {
    @GET
    @Path("/size")
    fun count() = repository.size()

    @GET
    @Path("{_id}")
    fun findById(@PathParam("_id") id: String, @QueryParam("summary") @DefaultValue("true") summary: Boolean) : Any? {
        val entity = baseEntityFromPath(id)
        return entity.mappedWith(mapper, summary)
    }

    @GET
    fun list(
        @QueryParam("first") firstPage: Long,
        @QueryParam("last") lastPage: Long,
        @QueryParam("query") @DefaultValue("") query: String,
        @QueryParam("field") @DefaultValue("_id") sortableField: String,
        @QueryParam("order") @DefaultValue("DESCENDING") sortOrder: SortOrder,
        @QueryParam("summary") @DefaultValue("true") summary: Boolean
    ) : Collection<Any?> {
        return repository.list(
            query = query,
            sort = Sort(sortableField, sortOrder),
            page = Page(firstPage, lastPage)
        ).mappedWith(mapper = mapper, summary = summary)
    }

    @POST
    fun save(dto: DTO): DTO {
        val entity = mapper.unmap(dto)
        repository.insert(entity)
        return mapper.mapFull(entity)
    }

    @PUT
    @Path("{_id}")
    fun update(@PathParam("_id") id: String, dto: DTO) : DTO {
        val entity = mapper.unmap(dto)

        when {
            id != entity._id -> throw NotAcceptableHttpException("Uri ID [$id] e Body ID [${entity._id}] incompatíveis!")
            !repository.exists(id) -> throw NotFoundHttpException("A entitidade com id $id não existe!")
        }

        repository.update(entity)
        return mapper.mapFull(entity)
    }

    @DELETE
    @Path("{_id}")
    fun delete(@PathParam("_id") id: String) {
        val entity = baseEntityFromPath(id)
        repository.remove(entity)
    }

    private fun baseEntityFromPath(id: String) = repository.findById(id) ?: throw NotFoundHttpException("Entidade com id $id não encontrada!")
}


