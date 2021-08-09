package org.edudev.arch.auth.functionality.permission

import dev.morphia.annotations.Entity
import org.edudev.arch.auth.functionality.GlobalFunctionality
import org.edudev.arch.auth.functionality.action.CrudAction

@Entity("permissions", useDiscriminator = false)
data class Permission(
    val functionality: GlobalFunctionality,
    val actions: Collection<CrudAction> = emptyList()
) {
    fun containsAction(crudAction: CrudAction) = actions.contains(crudAction)
}