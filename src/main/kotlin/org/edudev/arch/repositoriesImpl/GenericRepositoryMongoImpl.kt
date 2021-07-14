package org.edudev.arch.repositoriesImpl

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import dev.morphia.Datastore
import dev.morphia.Morphia
import org.bson.BsonDocument
import org.bson.BsonRegularExpression
import org.bson.conversions.Bson
import org.edudev.arch.db.MongoConfig
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.Entity
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.extensions.findAnnotationRecursively
import org.edudev.arch.extensions.page
import org.edudev.arch.extensions.sort
import org.edudev.arch.repositories.Repository


open class GenericRepositoryMongoImpl<T : DomainEntity>(
    entityClass: Class<T>,
    mongoConfig: MongoConfig
) : Repository<T> {

    private val collectionName = entityClass.findAnnotationRecursively(Entity::class.java)
        ?.value
        ?.takeIf { it.isNotEmpty() }
        ?: entityClass.simpleName
        ?: throw IllegalArgumentException("Can't get collection name 4 $entityClass /;")

    private val client = MongoClients.create(mongoConfig.url)

    private val datastore = Morphia.createDatastore(client, mongoConfig.url.database!!)

    init {
        datastore.mapper.mapPackage("org.edudev.domain")
    }

    private val collection: MongoCollection<T> = datastore.database.getCollection(collectionName, entityClass)

    protected open fun findWith(vararg conditions: Bson?): FindIterable<T> = findWith(listOf(*conditions))

    protected open fun findWith(conditions: Collection<Bson?>): FindIterable<T> = conditions.filterNotNull().let {
        if (it.isEmpty()) this.collection.find()
        else this.collection.find(and(*it.toTypedArray()))
    }

    override fun findById(id: String) = collection
        .find(eq("_id", id))
        .firstOrNull()

    override fun list(query: String, sort: Sort, page: Page): Collection<T> {
        return textSearch(query)
            .sort(sort)
            .page(page)
            .loadAsList()
    }

    override fun size(): Long = collection.countDocuments()

    override fun insert(entity: T) {
        collection.insertOne(entity)
    }

    override fun update(entity: T) {
        collection.replaceOne(eq(entity._id), entity)
    }

    override fun remove(entity: T) {
        collection.deleteOne(eq(entity._id))
    }

    private fun textSearch(value: String?): FindIterable<T> =
        if (value.isNullOrEmpty()) collection.find() else this.collection.find(
            BsonDocument("name", BsonRegularExpression(value))
        )

    private fun <L : Any> FindIterable<L>.loadAsList(): ArrayList<L> = this.toCollection(ArrayList())

}




