package org.edudev.arch.auth

import javax.ws.rs.NameBinding

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

@NameBinding
@Retention(RUNTIME)
@Target(FUNCTION, CLASS)
@MustBeDocumented
annotation class Restricted