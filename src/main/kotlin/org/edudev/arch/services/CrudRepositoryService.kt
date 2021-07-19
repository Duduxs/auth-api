package org.edudev.arch.services

import com.mongodb.MongoWriteException
import dev.morphia.query.UpdateException
import mu.KLogging
import org.edudev.arch.auth.Restricted
import org.edudev.arch.domain.*
import org.edudev.arch.dtos.EntityDTOMapper
import org.edudev.arch.exceptions.*
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
    @Path("{id}")
    @Restricted
    fun findById(@PathParam("id") id: String, @QueryParam("summary") @DefaultValue("true") summary: Boolean): Any? {
        val entity = baseEntityFromPath(id)
        return entity.mappedWith(mapper, summary)
    }

    @GET
    @Restricted
    fun list(
        @QueryParam("first") @DefaultValue("0") firstPage: Int,
        @QueryParam("last") @DefaultValue("10") lastPage: Int,
        @QueryParam("q") @DefaultValue("") query: String,
        @QueryParam("field") @DefaultValue("id") sortableField: String,
        @QueryParam("order") @DefaultValue("DESC") sortOrder: SortOrder,
        @QueryParam("summary") @DefaultValue("true") summary: Boolean
    ): Collection<Any?> {

        when {
            firstPage < 0 || lastPage < 0 -> throw BadRequestHttpException("Query params first $firstPage ou last $lastPage não devem ser menores que zero.")
            firstPage > lastPage -> throw BadRequestHttpException("Query params first $firstPage não deve ser maior que last $lastPage.")
        }

        return repository.list(
            query = query,
            sort = Sort(sortableField, sortOrder),
            page = Page(firstPage, lastPage)
        ).mappedWith(mapper = mapper, summary = summary)
    }

    @POST
    fun save(dto: DTO): DTO {
        val entity = mapper.unmap(dto)

        try {
            repository.insert(entity)
        } catch (e: MongoWriteException) {
            throw ConflictHttpException("Entidade com id ou email já cadastrados no banco")
        }

        return mapper.mapFull(entity)
    }

    @PUT
    @Path("{id}")
    @Restricted
    fun update(@PathParam("id") id: String, dto: DTO): DTO {
        val entity = mapper.unmap(dto)

        when {
            id != entity.id -> throw NotAcceptableHttpException("Uri ID [$id] e Body ID [${entity.id}] incompatíveis!")
            !repository.exists(id) -> throw NotFoundHttpException("A entitidade com id $id não existe!")
        }

        try {
            repository.update(entity)
        } catch (e : UpdateException){
            throw UnprocessableEntityHttpException("A entidade não recebeu novos dados para ser atualizada!")
        }

         return mapper.mapFull(entity)
    }

    @DELETE
    @Path("{id}")
    @Restricted
    fun delete(@PathParam("id") id: String) {
        val entity = baseEntityFromPath(id)
        repository.remove(entity)
    }

    companion object : KLogging()

    private fun baseEntityFromPath(id: String) =
        repository.findById(id) ?: throw NotFoundHttpException("Entidade com id $id não encontrada!")

}


