package org.edudev.arch.repositoriesImpl

import com.mongodb.client.MongoClients
import dev.morphia.Morphia
import dev.morphia.query.FindOptions
import dev.morphia.query.Query
import dev.morphia.query.experimental.filters.Filter
import dev.morphia.query.experimental.filters.Filters.*
import org.edudev.arch.db.MongoConfig
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.extensions.paged
import org.edudev.arch.extensions.sorted
import org.edudev.arch.repositories.Repository
import org.edudev.domain.properties.Property
import org.edudev.domain.users.User

open class GenericRepositoryMongoImpl<T : DomainEntity>(
    val entityClass: Class<T>,
    mongoConfig: MongoConfig
) : Repository<T> {

    private val client = MongoClients.create(mongoConfig.url)

    private val datastore = Morphia.createDatastore(client, mongoConfig.url.database!!)

    init {
        datastore.mapper.map(User::class.java, Property::class.java)
        datastore.ensureIndexes()
    }

    protected open fun findWith(vararg conditions: Filter?): Query<T> = findWith(listOf(*conditions))

    protected open fun findWith(conditions: Collection<Filter?>): Query<T> = conditions.filterNotNull().let {
        if (it.isEmpty()) datastore.find(entityClass)
        else datastore.find(entityClass).filter(and(*it.toTypedArray()))
    }

    override fun insert(entity: T) {
        datastore.insert(entity)
    }

    override fun update(entity: T) {
        datastore.merge(entity)
    }

    override fun remove(entity: T) {
        datastore.delete(entity)
    }

    override fun size(): Long = datastore
        .find(entityClass)
        .count()

    override fun findById(id: String) = datastore
        .find(entityClass)
        .filter(eq("_id", id))
        .firstOrNull()

    override fun list(query: String, sort: Sort, page: Page): Collection<T> = datastore
        .find(entityClass)
        .textSearch(query)
        .iterator(
            FindOptions()
                .sorted(sort)
                .paged(page)
        ).toList()

    private fun Query<T>.textSearch(value: String?): Query<T> =
        if (value.isNullOrEmpty()) datastore.find(entityClass)
        else datastore.find(entityClass).filter(text(value))
}




