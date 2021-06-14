package org.edudev.arch.repositories

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.Sort
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.*


interface CrudRepositoryService<Entity : DomainEntity, DTO: Any, DTO_SUMMARY>{

    @GET
    @Path("/size")
    fun count() : Long

    @GET
    fun list(
        @QueryParam("query") @DefaultValue("") query: String,
        @BeanParam sort: List<Sort>,
        @QueryParam("first") @DefaultValue("0") first: Long,
        @QueryParam("last") @DefaultValue("10") last: Long,
        @QueryParam("summary") @DefaultValue("true") summary: Boolean
    ) : Collection<Any?>

    @GET
    @Path("{_id}")
    fun findById(@PathParam("_id") id: String, @QueryParam("summary") @DefaultValue("true") summary: Boolean) : Any?

    @POST
    fun save(dto: DTO): DTO

    @PUT
    @Path("{_id}")
    fun update(@PathParam("_id") id: String, dto: DTO) : DTO

    @DELETE
    @Path("{_id}")
    fun delete(@PathParam("_id") id: String)
}