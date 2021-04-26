package org.edudev

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
    val col = database.getCollection<Jedi>()

    @POST
    fun create() {
        col.insertOne(Jedi("Eduardo José"))
    }

}