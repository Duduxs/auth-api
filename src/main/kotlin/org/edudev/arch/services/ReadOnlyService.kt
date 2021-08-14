package org.edudev.arch.services

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.NoArg
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.dtos.EntityDTOMapper
import org.edudev.arch.exceptions.BadRequestHttpException
import org.edudev.arch.exceptions.NotFoundHttpException
import org.edudev.arch.extensions.mappedWith
import org.edudev.arch.repositories.Repository
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.*

@NoArg
open class ReadOnlyService<T : DomainEntity, DTO : Any, DTO_S>(
    private val repository: Repository<T>,
    private val mapper: EntityDTOMapper<T, DTO, DTO_S>
) {

    @GET
    @Path("/size")
    open fun count() = repository.size()

    @GET
    @Path("{id}")
    open fun findById(
        @PathParam("id") id: String,
        @QueryParam("summary") @DefaultValue("true") summary: Boolean
    ): Any? {
        val entity = baseEntityFromPath(id)
        return entity.mappedWith(mapper, summary)
    }

    @GET
    open fun list(
        @BeanParam page: Page,
        @BeanParam sort: Sort,
        @QueryParam("q") @DefaultValue("") query: String,
        @QueryParam("summary") @DefaultValue("true") summary: Boolean
    ): Collection<Any?> {
        when {
            page.first < 0 || page.last < 0 ->
                throw BadRequestHttpException("Query params first ${page.first} ou last ${page.last} não devem ser menores que zero.")
            page.first > page.last ->
                throw BadRequestHttpException("Query params first ${page.first} não deve ser maior que last ${page.last}.")
        }

        return repository.list(
            query = query,
            sort = sort,
            page = page
        ).mappedWith(mapper = mapper, summary = summary)
    }

    protected fun baseEntityFromPath(id: String) = repository.findById(id) ?: throw NotFoundHttpException("Entidade com id $id não encontrada!")

}