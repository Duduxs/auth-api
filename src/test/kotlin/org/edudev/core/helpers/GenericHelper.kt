package org.edudev.core.helpers

import org.assertj.core.api.Assertions.assertThat
import org.edudev.arch.domain.DomainEntity

interface GenericHelper<E : DomainEntity, DTO : Any, DTO_S> {

    fun createDTO(domain: E): DTO

    fun createSummaryDTO(domain: E): DTO_S

    fun assertEquals(entity: E, dto: DTO): Any = assertThat(entity)
        .usingRecursiveComparison()
        .isEqualTo(dto)

    fun assertCollectionEquals(entities: Collection<E>, dtos: Collection<DTO>): Any = assertThat(entities)
        .usingRecursiveComparison()
        .isEqualTo(dtos)

}