package org.edudev.domain.users

import org.edudev.arch.repositories.Repository

interface Users : Repository<User> {

    fun existsByUsernameAndPassword(username: String, password: String): Boolean

}