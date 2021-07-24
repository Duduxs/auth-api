package org.edudev.domain.users

import org.edudev.arch.services.CrudService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class UsersService @Inject constructor(
    val users: Users,
    userDTOMapper: UserDTOMapper
) : CrudService<User, UserDTO, UserDTO>(
    repository = users,
    mapper = userDTOMapper
)

