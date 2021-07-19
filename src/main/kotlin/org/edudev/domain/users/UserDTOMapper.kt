package org.edudev.domain.users

import org.edudev.arch.dtos.EntityDTOMapper
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class UserDTOMapper @Inject constructor(
    users: Users
) : EntityDTOMapper<User, UserDTO, UserDTO>(
    dtoType = UserDTO::class,
    fullMapper = { UserDTO(it) },
    summaryMapper = { UserDTO(it) },
    fullUnmapper = { dto -> dto.update(users.findById(dto.id) ?: User(dto.id)) }
)