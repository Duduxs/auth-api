package org.edudev.domain.properties

import org.edudev.arch.db.MongoConfig
import org.edudev.arch.repositoriesImpl.GenericRepositoryMongoImpl
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class PropertiesMongoImpl @Inject constructor(
    mongoConfig: MongoConfig
) : GenericRepositoryMongoImpl<Property>(
    entityClass = Property::class.java,
    mongoConfig = mongoConfig
), Properties



