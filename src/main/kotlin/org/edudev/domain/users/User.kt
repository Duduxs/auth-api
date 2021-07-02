package org.edudev.domain.users

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.Entity
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity("Users")
class User(
    override val _id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    @field:NotNull var email: String = ""

    var name: String = ""

    @field:NotNull var password: String = ""
}