package org.edudev

import org.edudev.domain.states.Region
import org.edudev.domain.states.State
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

data class Jedi(val name: String)

@Path("/hello-resteasy")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GreetingResource {

    val client = KMongo.createClient()
    val database = client.getDatabase("test")
    val col = database.getCollection<State>()

    @POST
    fun create(): String {
        col.insertOne(State( name = "Pernambuco", habitats = 25000, region = Region.NORTH))
        return "Posted!"
    }

    @GET
    fun eae(): String {
        return "HI!"
    }
}