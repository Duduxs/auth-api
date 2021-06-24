package org.edudev.core.helpers

import org.assertj.core.api.Assertions.assertThat
import org.edudev.arch.domain.DomainEntity


fun <E : DomainEntity> E.assertEquals(dto: Any) {
    assertThat(this)
        .usingRecursiveComparison()
        .isEqualTo(dto)
}

fun <E : Collection<DomainEntity>> E.assertCollectionEquals(dtos: Collection<Any>) {
    assertThat(this)
        .usingRecursiveComparison()
        .isEqualTo(dtos)
}

fun <E: Any> E.setNewId(id: String) {
    this::class.java.getDeclaredField("_id").also { field ->
        field.isAccessible = true
        field.set(this, id)
    }
}