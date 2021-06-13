package org.edudev.arch.repositories

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.edudev.arch.domain.DomainEntity
import org.jboss.resteasy.annotations.jaxrs.PathParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path


interface CrudRepository<Entity : DomainEntity, DTO: Any, DTO_SUMMARY>{

    @GET
    @Path("/size")
    fun count() : Long

    @GET
    @Path("{id}")
    fun findById(@PathParam("id") id: String): DTO?

    @POST
    fun save(dto: DTO): DTO
}