package org.edudev.arch.repositories

import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.domain.DomainEntity

interface ReadOnlyRepository<T : DomainEntity> {

    fun findById(id: String) : T?

    fun exists(id: String) = findById(id) != null

    fun list(query: String?, sort: List<Sort>, page: Page?): Collection<T>

    fun size() : Long

}