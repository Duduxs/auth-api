package org.edudev.domain.users

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import org.edudev.arch.domain.DomainEntity
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity("Users", )
class User(
    @Id override val _id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    @field:NotNull var email: String = ""

    var username: String = ""

    @field:NotNull var password: String = ""
}