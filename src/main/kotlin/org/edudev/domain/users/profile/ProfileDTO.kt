package org.edudev.domain.users.profile

import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.arch.extensions.throwIfViolate
import org.edudev.arch.extensions.validate
import java.util.*

data class ProfileDTO(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val permissions: Collection<Permission> = emptyList()
) {
    constructor(profile: Profile) : this(
        id = profile.id,
        name = profile.name,
        permissions = profile.permissions
    )

    fun update(
        profile: Profile
    ) = profile.also {
        require(it.id == id) { "Incompatible Id" }
        it.name = name
        it.permissions = permissions
        it.validate().throwIfViolate()
    }
}

fun Profile.toDTO() = ProfileDTO(this)