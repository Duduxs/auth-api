package org.edudev.arch.domain

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.TYPE

@Target(TYPE, CLASS)
@Retention(RUNTIME)
@MustBeDocumented
annotation class Entity(val value: String = "")