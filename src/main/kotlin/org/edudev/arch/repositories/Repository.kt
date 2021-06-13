package org.edudev.arch.repositories

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import javax.enterprise.context.ApplicationScoped



interface Repository<T : DomainEntity> : ReadOnlyRepository<T>, InsertOnlyRepository<T>, UpdateOnlyRepository<T>, DeleteOnlyRepository<T>{

    companion object {
        fun <T : DomainEntity> empty(): Repository<T> {
            return object : Repository<T> {

                override fun remove(entity: T) = throw UnsupportedOperationException("Method not implemented!")

                override fun removeAll(entities: Collection<T>) = throw UnsupportedOperationException("Method not implemented!")

                override fun insert(entity: T) = throw UnsupportedOperationException("Method not implemented!")

                override fun findById(id: String) = throw UnsupportedOperationException("Method not implemented!")

                override fun list(query: String?, sort: Sort?, page: Page?) = throw UnsupportedOperationException("Method not implemented!")

                override fun size() = throw UnsupportedOperationException("Method not implemented!")

                override fun update(entity: T) = throw UnsupportedOperationException("Method not implemented!")
            }
        }
    }
}