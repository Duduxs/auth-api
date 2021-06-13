package org.edudev.arch.repositories

import org.edudev.arch.domain.DomainEntity
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.*


interface CrudRepositoryService<Entity : DomainEntity, DTO: Any, DTO_SUMMARY>{

    @GET
    @Path("/size")
    fun count() : Long

    @GET
    @Path("{id}")
    fun findById(@PathParam("id") id: String, @QueryParam("summary") @DefaultValue("true") summary: Boolean) : Any?

    @POST
    fun save(dto: DTO): DTO
}