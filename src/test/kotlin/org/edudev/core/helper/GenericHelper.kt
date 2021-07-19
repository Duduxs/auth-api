package org.edudev.core.helper

import org.assertj.core.api.Assertions.assertThat
import org.edudev.arch.domain.DomainEntity
import java.lang.reflect.Field


fun <E : DomainEntity> E.assertEquals(dto: Any) {
    assertThat(this)
        .usingRecursiveComparison()
        .isEqualTo(dto)
}

fun <E : DomainEntity> E.assertSummaryEquals(dto: Any) {
    val fieldsIgnored: Array<String> = getSummaryMissingFieldsName(
        entityFields = this::class.java::getDeclaredFields.invoke(),
        dtoFields = dto::class.java::getDeclaredFields.invoke(),
    )
    assertThat(this)
        .usingRecursiveComparison()
        .ignoringFields(*fieldsIgnored)
        .isEqualTo(dto)
}

fun <E : Collection<DomainEntity>> E.assertCollectionEquals(dtos: Collection<Any>) {
    assertThat(this)
        .usingRecursiveComparison()
        .isEqualTo(dtos)
}

fun <E: Any> E.setNewId(id: String) {
    this::class.java.getDeclaredField("id").also { field ->
        field.isAccessible = true
        field.set(this, id)
    }
}

fun getSummaryMissingFieldsName(entityFields: Array<Field>, dtoFields: Array<Field>) = entityFields
    .filter { it.name !in dtoFields.map { s -> s.name } }
    .map { it.name }
    .toTypedArray()

fun String?.orDefaultTestPort() = this ?: "8084"

fun String.putSlashAtStartIfDontHave() = if (this.startsWith("/")) this else "/$this"