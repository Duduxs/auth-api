package org.edudev.domain.users

import org.edudev.arch.repositories.Repository

interface Users : Repository<User> {

    fun findByUsernameAndPassword(username: String, password: String): User?

}