package org.edudev.arch.repositoriesImpl

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.text
import org.edudev.arch.domain.Entity
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.exceptions.EntityNotFoundException
import org.edudev.arch.extensions.findAnnotationRecursively
import org.edudev.arch.extensions.page
import org.edudev.arch.extensions.sort
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.repositories.AuthRepository
import org.litote.kmongo.KMongo
import org.litote.kmongo.and
import org.litote.kmongo.withKMongo


open class GenericRepositoryMongoImpl<T : DomainEntity>(
    private val entityClass: Class<T>,
) : AuthRepository<T> {

    private val mongoClient = KMongo.createClient()

    private val database = mongoClient.getDatabase(DATABASE_NAME)

    private val collectionName = entityClass.findAnnotationRecursively(Entity::class.java)
        ?.value
        ?.takeIf { it.isNotEmpty() }
        ?: entityClass.simpleName
        ?: throw IllegalArgumentException("Can't get collection name 4 $entityClass /;")

    private val collection: MongoCollection<T> = database.getCollection(collectionName, entityClass).withKMongo()

    override fun findById(id: String) = collection.find(eq("id", id))
        .takeIf { entityClass.isInstance(it) }
        ?.first()
        ?: throw EntityNotFoundException(id)

    override fun list(query: String?, sort: Sort?, page: Page?): Collection<T> = textSearch(query)
        .sort(sort)
        .page(page)
        .loadAsList()

    override fun size(query: String): Long = collection.countDocuments()

    override fun insert(entity: T) {
        collection.insertOne(entity)
    }

    override fun update(entity: T): T {
        val result = findById(entity.id)
        collection.replaceOne(eq(entity.id), result)
        return result
    }

    override fun remove(entity: T) {
        collection.deleteOne(eq(entity.id))
    }

    override fun removeAll(entities: Collection<T>) {
        collection.deleteMany(eq("id", entities))
    }

    private fun textSearch(value: String?): FindIterable<T> =
        if (value.isNullOrEmpty()) collection.find() else this.collection.find(and(text(value)))

    private fun <L : Any> FindIterable<L>.loadAsList(): ArrayList<L> = this.toCollection(ArrayList())

    companion object {
        private const val DATABASE_NAME = "auth-api"
    }
}


