package org.edudev.domain.properties

import org.edudev.domain.properties.directionalities.Directionality
import org.edudev.domain.users.User
import org.edudev.domain.users.UserDTO
import org.edudev.domain.users.toDTO
import java.util.*


data class PropertyDTO(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val address: String,
    val directionality: Directionality,
    val value: Double,
    val user: UserDTO?
) {

    constructor(property: Property) : this(
        property.id,
        property.name,
        property.address,
        property.directionality,
        property.value,
        property.user?.toDTO()
    )

    fun update(
        property: Property,
        userSearch: (String) -> User?
    ) = property.also {
        require(it.id == id) { "Incompatible Id!" }
        it.name = name
        it.address = address
        it.directionality = directionality
        it.value = value
        it.user = user?.id?.let { userSearch(it) }
    }
}
