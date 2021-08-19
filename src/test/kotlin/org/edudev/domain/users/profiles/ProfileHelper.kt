package org.edudev.domain.users.profiles


import org.edudev.domain.users.profile.Profile
import org.edudev.domain.users.profile.ProfileDTO
import org.junit.jupiter.api.Assertions.assertEquals

infix fun Profile.assertEquals(dto: ProfileDTO){
    assertEquals(this.id, dto.id)
    assertEquals(this.name, dto.name)

    if(!this.permissions.isEmpty() && !dto.permissions.isEmpty()){
        assertEquals(this.permissions.size,dto.permissions.size)

        this.permissions.forEachIndexed { i, permission ->
            val dtoPermission = dto.permissions.elementAt(i)
            assertEquals(permission.functionality, dtoPermission.functionality)

            if(!permission.actions.isEmpty() && !dtoPermission.actions.isEmpty()){
                permission.actions.forEach { crudAction ->
                    assertEquals(dtoPermission.containsAction(crudAction), true)
                }
            }
        }
    }
}
