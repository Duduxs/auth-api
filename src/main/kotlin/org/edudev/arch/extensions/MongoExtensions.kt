package org.edudev.arch.extensions

import com.mongodb.client.FindIterable
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.edudev.arch.domain.Page
import org.edudev.arch.domain.Sort
import org.edudev.arch.domain.SortOrder


fun <T> FindIterable<T>.sort(order: Sort?): FindIterable<T> = if (order == null) this else {
    val result = BsonDocument()
        .append(
            order.field,
            BsonInt32(if (order.order == SortOrder.DESCENDING) -1 else 1)
        )

    this.sort(result)
}

fun <T> FindIterable<T>.page(page: Page?): FindIterable<T> =
    if (page == null) this else this.skip(page.first.toInt()).limit(page.last.toInt())

