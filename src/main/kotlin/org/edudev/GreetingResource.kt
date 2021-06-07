package org.edudev

import org.edudev.domain.states.City
import org.edudev.domain.states.Region
import org.edudev.domain.states.State
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/hello-resteasy")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GreetingResource {





    @POST
    fun create(): String {

//        col.insertOne(State( name = "Pernambuco", habitats = 25000, region = Region.NORTH))
        return "Posted!"
    }


}