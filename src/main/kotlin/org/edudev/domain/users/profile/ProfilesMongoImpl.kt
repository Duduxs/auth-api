package org.edudev.domain.users.profile

import org.edudev.arch.db.MongoConfig
import org.edudev.arch.repositoriesImpl.GenericRepositoryMongoImpl
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class ProfilesMongoImpl @Inject constructor(
    mongoConfig: MongoConfig
) : GenericRepositoryMongoImpl<Profile>(
    entityClass = Profile::class.java,
    mongoConfig = mongoConfig
), Profiles