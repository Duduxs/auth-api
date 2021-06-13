package org.edudev

import org.edudev.properties.Properties
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/hello-resteasy")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GreetingResource {

    @Inject
    lateinit var properties: Properties

    @GET
    fun create(): Long {

//        col.insertOne(State( name = "Pernambuco", habitats = 25000, region = Region.NORTH))
        return properties.size()
    }


}