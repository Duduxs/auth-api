package org.edudev.domain.users

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
), Users