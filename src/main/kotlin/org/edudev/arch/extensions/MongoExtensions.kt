package org.edudev.arch.extensions

import com.mongodb.client.FindIterable
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.domain.SortOrder


fun <T> FindIterable<T>.sort(order: List<Sort>): FindIterable<T> = if (order.isEmpty()) this else {
    val result = BsonDocument()
    for ((field, type) in order) {
        result.append(
            field,
            BsonInt32(if (type == SortOrder.DESCENDING) -1 else 1)
        )
    }
    this.sort(result)
}

fun <T> FindIterable<T>.page(page: Page?): FindIterable<T> =
    if (page == null) this else this.skip(page.first.toInt()).limit(page.last.toInt())

