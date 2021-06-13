package org.edudev.arch.dtos

import org.edudev.arch.domain.DomainEntity
import java.io.Serializable
import kotlin.reflect.KClass

open class EntityDTOMapper<T : DomainEntity, DTO : Any, DTO_SUMMARY>(
    private val dtoType: KClass<DTO>,
    private val fullMapper: (T) -> DTO,
    private val summaryMapper: (T) -> DTO_SUMMARY,
    private val fullUnmapper: (DTO) -> T
) : Serializable {

    fun unmap(dto: DTO) = fullUnmapper(dto)

    fun mapFull(entity: T) = fullMapper(entity)

    fun mapSummary(entity: T) = summaryMapper(entity)


}