package org.edudev.domain.users.profile

import com.sun.istack.NotNull
import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import org.edudev.arch.auth.functionality.GlobalFunctionality
import org.edudev.arch.auth.functionality.permission.Permission
import org.edudev.arch.domain.DomainEntity
import java.util.*

@Entity(value = "profiles", useDiscriminator = false)
data class Profile(
    @Id override val id: String = UUID.randomUUID().toString(),
) : DomainEntity {

    @field:NotNull
    var name: String = ""

    var permissions: Collection<Permission> = emptyList()

    fun hasPermissionBy(functionality: GlobalFunctionality) = permissions.any { it.functionality == functionality }

    fun findPermissionBy(functionality: GlobalFunctionality) = permissions.find { it.functionality == functionality }
}