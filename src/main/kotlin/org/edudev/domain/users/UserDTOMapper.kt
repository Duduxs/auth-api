package org.edudev.domain.users

import org.edudev.arch.dtos.EntityDTOMapper
import org.edudev.domain.users.profile.Profiles
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class UserDTOMapper @Inject constructor(
    users: Users,
    profiles: Profiles
) : EntityDTOMapper<User, UserDTO, UserSummaryDTO>(
    dtoType = UserDTO::class,
    fullMapper = { UserDTO(it) },
    summaryMapper = { UserSummaryDTO(it) },
    fullUnmapper = { dto ->
        dto.update(
            users.findById(dto.id) ?: User(dto.id),
            profiles::findById
        )
    }
)