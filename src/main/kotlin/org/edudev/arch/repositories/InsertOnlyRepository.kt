package org.edudev.arch.repositories

import org.edudev.arch.domain.DomainEntity

interface InsertOnlyRepository<T : DomainEntity> {

    fun insert(entity: T)
}