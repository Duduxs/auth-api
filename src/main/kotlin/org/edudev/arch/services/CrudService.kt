package org.edudev.arch.services

import Main.logger
import com.mongodb.MongoWriteException
import dev.morphia.query.UpdateException
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.NoArg
import org.edudev.arch.dtos.EntityDTOMapper
import org.edudev.arch.exceptions.ConflictHttpException
import org.edudev.arch.exceptions.NotAcceptableHttpException
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.repositories.Repository
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.DELETE
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@NoArg
open class CrudService<T : DomainEntity, DTO : Any, DTO_S>(
    private val repository: Repository<T>,
    private val mapper: EntityDTOMapper<T, DTO, DTO_S>
) : ReadOnlyService<T, DTO, DTO_S> (
    repository = repository,
    mapper = mapper
) {

    @Context
    lateinit var uriInfo: UriInfo

    @POST
    open fun save(dto: DTO): Response {
        val entity = mapper.unmap(dto)

        try {
            repository.insert(entity)
        } catch (e: MongoWriteException) {
            logger.error { e }
            throw ConflictHttpException("Entidade com dados já cadastrados no banco")
        }

        val uri = uriInfo
            .absolutePathBuilder
            .path("{id}")
            .resolveTemplate("id", entity.id)
            .build()

        return Response.created(uri).entity(mapper.mapFull(entity)).build()
    }

    @PUT
    @Path("{id}")
    open fun update(@PathParam("id") id: String, dto: DTO): DTO {
        val entity = mapper.unmap(dto)

        when {
            id != entity.id -> throw NotAcceptableHttpException("Uri ID [$id] e Body ID [${entity.id}] incompatíveis!")
            !repository.exists(id) -> throw NotFoundHttpException("A entidade com id $id não existe!")
        }

        try {
            repository.update(entity)
        } catch (e: UpdateException) {
            logger.warn { " * * Como não foram passados novos dados para a entidade a mesma não será atualizada. * * "}
        }

        return mapper.mapFull(entity)
    }

    @DELETE
    @Path("{id}")
    open fun delete(@PathParam("id") id: String) {
        val entity = baseEntityFromPath(id)
        repository.remove(entity)
    }

}


