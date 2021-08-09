package org.edudev.domain.properties

import org.edudev.arch.db.MongoConfig
import org.edudev.arch.extensions.eqRef
import org.edudev.arch.extensions.fieldName
import org.edudev.arch.repositoriesImpl.GenericRepositoryMongoImpl
import org.edudev.domain.users.User
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class PropertiesMongoImpl @Inject constructor(
    mongoConfig: MongoConfig,
) : GenericRepositoryMongoImpl<Property>(
    entityClass = Property::class.java,
    mongoConfig = mongoConfig
), Properties {

    override fun listByUser(user: User): List<Property> = findWith(
            eqRef(fieldName(Property::user), user)
        ).toList()
}



