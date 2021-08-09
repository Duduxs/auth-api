package org.edudev.arch.extensions

import com.mongodb.DBRef
import dev.morphia.annotations.Entity
import dev.morphia.query.FindOptions
import dev.morphia.query.experimental.filters.Filter
import org.bson.BsonInt32
import org.bson.Document
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.domain.SortOrder.DESC
import dev.morphia.query.experimental.filters.Filters.eq
import kotlin.reflect.KProperty


fun FindOptions.sorted(sort: Sort): FindOptions {
    val document = Document(
        sort.field,
        BsonInt32(if (sort.type == DESC) -1 else 1)
    )

   return this.sort(document)
}

fun FindOptions.paged(page: Page): FindOptions = this.skip(page.first).limit(page.last)

fun fieldName(vararg properties: KProperty<*>) = properties.joinToString(".") { it.name }

fun <T: DomainEntity> eqRef(field: String, value: T): Filter = eq(field, value.toDbRef())

fun <T : DomainEntity> T.toDbRef() = DBRef(this.getCollectionName(), this.id)

fun <T : DomainEntity> T.getCollectionName() = this.javaClass.getAnnotation(Entity::class.java).value
