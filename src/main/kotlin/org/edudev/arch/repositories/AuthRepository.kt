package org.edudev.arch.repositories

import org.edudev.arch.domain.DomainEntity

interface AuthRepository<T : DomainEntity> : ReadOnlyRepository<T>, InsertOnlyRepository<T>, UpdateOnlyRepository<T>, DeleteOnlyRepository<T>{

    companion object {
        fun <T : DomainEntity> empty(): AuthRepository<T> {
            return object : AuthRepository<T> {

                override fun remove(entity: T) = throw UnsupportedOperationException("Method not implemented!")

                override fun removeAll(entities: Collection<T>) = throw UnsupportedOperationException("Method not implemented!")

                override fun insert(entity: T) = throw UnsupportedOperationException("Method not implemented!")

                override fun findById(id: String) = throw UnsupportedOperationException("Method not implemented!")

                override fun list(pageNum: Int, pageSize: Int) = throw UnsupportedOperationException("Method not implemented!")

                override fun size(query: String) = throw UnsupportedOperationException("Method not implemented!")

                override fun update(entity: T) = throw UnsupportedOperationException("Method not implemented!")
            }
        }
    }
}