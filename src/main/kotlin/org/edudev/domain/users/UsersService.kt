package org.edudev.domain.users

import mu.KLogging
import org.edudev.arch.auth.AuthenticatedUser
import org.edudev.arch.auth.Restricted
import org.edudev.arch.auth.functionality.GlobalFunctionality.USERS
import org.edudev.arch.services.CrudService
import org.edudev.domain.properties.Properties
import org.edudev.domain.properties.PropertyDTOMapper
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.SecurityContext

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Restricted(USERS)
class UsersService @Inject constructor(
    val users: Users,
    val properties: Properties,
    val userDTOMapper: UserDTOMapper,
    val propertyDTOmapper: PropertyDTOMapper,
) : CrudService<User, UserDTO, UserSummaryDTO>(
    repository = users,
    mapper = userDTOMapper
) {

    @Context
    lateinit var securityContext: SecurityContext

    private val currentAuthenticated
        get() = securityContext.userPrincipal as AuthenticatedUser

    @GET
    @Path("/properties")
    fun listUserProperties(
        @QueryParam("summary") @DefaultValue("true") summary: Boolean,
    ): Any? = properties.listByUser(currentAuthenticated.user).map { propertyDTOmapper.map(it, summary) }



    companion object : KLogging()
}
