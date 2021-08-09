package org.edudev.domain.users.profile

import org.edudev.arch.dtos.EntityDTOMapper
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class ProfileDTOMapper @Inject constructor(
    profiles: Profiles
) : EntityDTOMapper<Profile, ProfileDTO, ProfileDTO>(
    dtoType = ProfileDTO::class,
    fullMapper = { ProfileDTO(it) },
    summaryMapper = { ProfileDTO(it) },
    fullUnmapper = { dto ->
        dto.update(profiles.findById(dto.id) ?: Profile(dto.id))
    }
)