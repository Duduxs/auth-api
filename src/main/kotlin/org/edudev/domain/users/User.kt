package org.edudev.domain.users

import dev.morphia.annotations.*
import dev.morphia.utils.IndexType
import org.edudev.arch.domain.DomainEntity
import org.jetbrains.annotations.NotNull
import java.util.*

@Indexes(
    Index(fields = arrayOf(Field(value = "$**", type = IndexType.TEXT))),
    Index(fields = arrayOf(Field(value = "email")), options = IndexOptions(unique = true))
)
@Entity(value = "users", useDiscriminator = false)
class User(
    @Id override val id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    @field:NotNull var email: String = ""

    var username: String = ""

    @field:NotNull var password: String = ""
}