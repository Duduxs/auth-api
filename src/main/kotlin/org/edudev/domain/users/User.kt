package org.edudev.domain.users

import dev.morphia.annotations.*
import dev.morphia.utils.IndexType
import org.edudev.arch.domain.DomainEntity
import org.edudev.domain.users.profile.Profile
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.constraints.Email

@Indexes(
    Index(fields = arrayOf(Field(value = "$**", type = IndexType.TEXT))),
    Index(fields = arrayOf(Field(value = "username")), options = IndexOptions(unique = true)),
    Index(fields = arrayOf(Field(value = "email")), options = IndexOptions(unique = true))
)
@Entity(value = "users", useDiscriminator = false)
data class User(
    @Id override val id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    var name: String = ""

    @field:NotNull
    var username: String = ""

    @field:NotNull
    @field:Email
    var email: String = ""

    @field:NotNull
    var password: String = ""

    @field:Reference
    var profile: Profile? = null

}