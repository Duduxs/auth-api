package org.edudev.arch.extensions

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.dtos.EntityDTOMapper


fun <T : DomainEntity, DTO : Any, DTO_S> T.mappedWith(
    mapper: EntityDTOMapper<T, DTO, DTO_S>,
    summary: Boolean = true
) = mapper.map(entity = this, summary)

fun <T : DomainEntity, DTO : Any, DTO_S> Collection<T>.mappedWith(
    mapper: EntityDTOMapper<T, DTO, DTO_S>,
    summary: Boolean = true
) = this.map { mapper.map(entity = it, summary) }

