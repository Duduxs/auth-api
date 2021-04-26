package org.edudev

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

data class Jedi(val name: String)

@Path("/hello-resteasy")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GreetingResource {

    val client = KMongo.createClient()
    val database: MongoDatabase = client.getDatabase("test")
    val col = database.getCollection<Jedi>()

    @POST
    fun create() {
        col.insertOne(Jedi("Eduardo José"))
    }

}