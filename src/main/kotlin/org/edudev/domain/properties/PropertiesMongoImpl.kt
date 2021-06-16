package org.edudev.domain.properties

import org.edudev.arch.repositoriesImpl.GenericRepositoryMongoImpl
import javax.enterprise.context.Dependent

@Dependent
class PropertiesMongoImpl : GenericRepositoryMongoImpl<Property>(
    entityClass = Property::class.java
), Properties



