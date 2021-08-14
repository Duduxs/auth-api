package org.edudev.arch.extensions

import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.dtos.EntityDTOMapper
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation
import javax.validation.Validator


fun <T : DomainEntity, DTO : Any, DTO_S> T.mappedWith(
    mapper: EntityDTOMapper<T, DTO, DTO_S>,
    summary: Boolean = true
) = mapper.map(entity = this, summary)

fun <T : DomainEntity, DTO : Any, DTO_S> Collection<T>.mappedWith(
    mapper: EntityDTOMapper<T, DTO, DTO_S>,
    summary: Boolean = true
) = this.map { mapper.map(entity = it, summary) }

fun <T : DomainEntity> T.validate(): MutableSet<ConstraintViolation<T>?> {
    val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    return validator.validate(this)
}

fun <T: DomainEntity> MutableSet<ConstraintViolation<T>?>.throwIfViolate() {
    if(this.isEmpty()) return
    throw ConstraintViolationException(this)
}


