package org.edudev.arch.repositoriesImpl

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.BsonDocument
import org.bson.BsonRegularExpression
import org.edudev.arch.domain.*
import org.edudev.arch.extensions.findAnnotationRecursively
import org.edudev.arch.extensions.page
import org.edudev.arch.extensions.sort
import org.edudev.arch.repositories.Repository
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOneById


open class GenericRepositoryMongoImpl<T : DomainEntity>(
    private val entityClass: Class<T>,
    mongoConfig: MongoConfig
) : Repository<T> {

    private val database = KMongo.createClient(mongoConfig.url).getDatabase(mongoConfig.url.database!!)

    private val collectionName = entityClass.findAnnotationRecursively(Entity::class.java)
        ?.value
        ?.takeIf { it.isNotEmpty() }
        ?: entityClass.simpleName
        ?: throw IllegalArgumentException("Can't get collection name 4 $entityClass /;")

    private val collection: MongoCollection<T> = database.getCollection(collectionName, entityClass)

    override fun findById(id: String) = collection
        .findOneById(id)
        ?.takeIf { entityClass.isInstance(it) }

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

    override fun removeAll(entities: Collection<T>) {
        collection.deleteMany(eq("_id", entities))
    }

    private fun textSearch(value: String?): FindIterable<T> =
        if (value.isNullOrEmpty()) collection.find() else this.collection.find(
            BsonDocument("name", BsonRegularExpression(value))
        )

    private fun <L : Any> FindIterable<L>.loadAsList(): ArrayList<L> = this.toCollection(ArrayList())

}




