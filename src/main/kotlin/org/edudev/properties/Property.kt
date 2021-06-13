package org.edudev.properties

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.domain.Entity
import org.edudev.properties.directionalities.Directionality
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity("Properties")
data class Property(
    override val _id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    @field:NotNull
    var name: String = ""

    @field:NotNull
    var address: String = ""

    @field:NotNull var directionality: Directionality = Directionality.RENT

    @field:NotNull
    var value: Double = 0.0
}