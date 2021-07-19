package org.edudev.domain.properties

import dev.morphia.annotations.*
import dev.morphia.utils.IndexType
import org.edudev.arch.domain.DomainEntity
import org.edudev.domain.properties.directionalities.Directionality
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity("properties", useDiscriminator = false)
@Indexes(Index(fields = arrayOf(Field(value = "$**", type = IndexType.TEXT))))
data class Property(
    @Id override val id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    @field:NotNull
    var name: String = ""

    @field:NotNull
    var address: String = ""

    @field:NotNull var directionality: Directionality = Directionality.RENT

    @field:NotNull
    var value: Double = 0.0
}