package org.edudev.domain.properties

import org.edudev.arch.db.MongoConfig
import org.edudev.arch.extensions.eqRef
import org.edudev.arch.extensions.fieldName
import org.edudev.arch.repositoriesImpl.GenericRepositoryMongoImpl
import org.edudev.domain.properties.zipcodes.Address
import org.edudev.domain.properties.zipcodes.boundary.ViaZipCodeBoundary
import org.edudev.domain.users.User
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class PropertiesMongoImpl @Inject constructor(
    mongoConfig: MongoConfig,
    private val viaZipCodeBoundary: ViaZipCodeBoundary
) : GenericRepositoryMongoImpl<Property>(
    entityClass = Property::class.java,
    mongoConfig = mongoConfig
), Properties {

    override fun listByUser(user: User): List<Property> = findWith(
        eqRef(fieldName(Property::user), user)
    ).toList()

    override fun insert(entity: Property) {
        searchAddress(entity.address!!)?.let { entity.address = it }

        super.insert(entity)
    }

    override fun update(entity: Property) {
        searchAddress(entity.address!!)?.let { entity.address = it }

        super.update(entity)
    }

    private fun searchAddress(address: Address) = viaZipCodeBoundary
        .getByZipCode(address.zipCode)

}



