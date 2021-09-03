package org.edudev.domain.users

import dev.morphia.query.experimental.filters.Filters.eq
import org.edudev.arch.db.MongoConfig
import org.edudev.arch.repositoriesImpl.GenericRepositoryMongoImpl
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class UsersMongoImpl @Inject constructor(
    mongoConfig: MongoConfig,
) : GenericRepositoryMongoImpl<User>(
    entityClass = User::class.java,
    mongoConfig = mongoConfig
), Users {

    override fun findByUsername(username: String) = findWith(eq("username", username)).firstOrNull()

    override fun findByUsernameAndPassword(username: String, password: String): User? {
        return findWith(
            eq("username", username),
            eq("password", password)
        ).firstOrNull()

    }
}