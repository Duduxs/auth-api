package org.edudev.arch.auth

import org.edudev.arch.auth.functionality.GlobalFunctionality
import javax.ws.rs.NameBinding
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

@NameBinding
@Retention(RUNTIME)
@Target(FUNCTION, CLASS)
@MustBeDocumented
annotation class Restricted(val functionality: GlobalFunctionality)