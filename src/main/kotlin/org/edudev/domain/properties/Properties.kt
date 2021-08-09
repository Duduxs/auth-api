package org.edudev.domain.properties

import org.edudev.arch.repositories.Repository
import org.edudev.domain.users.User

interface Properties : Repository<Property> {

    fun listByUser(user: User) : List<Property>
}