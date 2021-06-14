package org.edudev.arch.extensions

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.dtos.EntityDTOMapper

fun <T : Any, A : Annotation> Class<T>.findAnnotationRecursively(annotation: Class<out A>): A? =
    this.getAnnotation(annotation)
        ?: (listOf(this.superclass) + this.interfaces.toMutableList())
            .asSequence()
            .filterNotNull()
            .map { it.findAnnotationRecursively(annotation) }
            .find { it != null }


fun <T : DomainEntity, DTO : Any, DTO_S> T.mappedWith(
    mapper: EntityDTOMapper<T, DTO, DTO_S>,
    summary: Boolean = true
) = mapper.map(entity = this, summary)

fun <T : DomainEntity, DTO : Any, DTO_S> Collection<T>.mappedWith(
    mapper: EntityDTOMapper<T, DTO, DTO_S>,
    summary: Boolean = true
) = this.map { mapper.map(entity = it, summary) }