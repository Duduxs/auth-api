package org.edudev.arch.repositories

import org.edudev.arch.domain.DomainEntity

interface DeleteOnlyRepository<T : DomainEntity> {

    fun remove(entity: T)

}