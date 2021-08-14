package org.edudev.domain.properties

import dev.morphia.annotations.*
import dev.morphia.utils.IndexType
import org.edudev.arch.domain.DomainEntity
import org.edudev.domain.properties.directionalities.Directionality
import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.users.User
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Entity("properties", useDiscriminator = false)
@Indexes(Index(fields = arrayOf(Field(value = "$**", type = IndexType.TEXT))))
data class Property(
    @Id override val id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    @field:NotBlank
    var name: String = ""

    @field:NotNull
    @field:Valid
    var address: Address? = null

    @field:NotNull var directionality: Directionality = Directionality.RENT

    @field:NotNull
    var value: Double = 0.0

    @field:Reference
    var user: User? = null
}