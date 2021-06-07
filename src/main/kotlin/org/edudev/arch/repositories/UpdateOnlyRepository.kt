package org.edudev.arch.repositories

import org.edudev.arch.domain.DomainEntity

interface UpdateOnlyRepository<T : DomainEntity> {

    fun update(entity: T) : T

}