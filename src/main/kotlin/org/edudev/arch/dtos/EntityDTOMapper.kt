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

    fun map(entity: T, summary: Boolean) = if (summary) this.mapSummary(entity) else this.mapFull(entity)

    fun mapFull(entity: T): DTO = this.fullMapper(entity)

    private fun mapSummary(entity: T): DTO_SUMMARY = this.summaryMapper(entity)

    fun unmap(dto: DTO) = fullUnmapper(dto)




}