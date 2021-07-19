package org.edudev.arch.extensions

import dev.morphia.query.FindOptions
import org.bson.BsonInt32
import org.bson.Document
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.domain.SortOrder.DESC


fun FindOptions.sorted(sort: Sort): FindOptions {
    val document = Document(
        sort.field,
        BsonInt32(if (sort.type == DESC) -1 else 1)
    )

   return this.sort(document)
}

fun FindOptions.paged(page: Page): FindOptions = this.skip(page.first).limit(page.last)
