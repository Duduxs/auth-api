package org.edudev.core.helpers

import org.assertj.core.api.Assertions.assertThat
import org.edudev.arch.domain.DomainEntity


fun <E : DomainEntity> E.assertEquals(dto: Any) = assertThat(this)
    .usingRecursiveComparison()
    .isEqualTo(dto)

fun <E : Collection<DomainEntity>> E.assertCollectionEquals(dtos: Collection<Any>): Any = assertThat(this)
    .usingRecursiveComparison()
    .isEqualTo(dtos)
