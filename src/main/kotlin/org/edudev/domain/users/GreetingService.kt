package org.edudev.domain.users


import com.mongodb.client.MongoClients
import dev.morphia.Morphia
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/greetings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class GreetingService {

    val user = User().also {
        it.username = "Duduxs"
        it.email = "Duduxss3@gmail.com"
        it.password = "Duduxs123"
    }

    final val client = MongoClients.create("mongodb://localhost:27017/auth-api")

    final val datastore = Morphia.createDatastore(client, "auth-api")

    init {
        datastore.mapper.mapPackage("org.edudev.domain")
    }

    @GET
    fun eae(): String{
        datastore.insert(user)
        return "Vê se inseriu mesmo"
    }

}