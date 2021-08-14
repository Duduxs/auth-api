package org.edudev.arch.extensions

import java.lang.reflect.Method
import kotlin.reflect.KClass

inline fun <reified T: Any> Any?.takeIfInstance() = this.takeIf { it is T }?.let { it as T }

fun Boolean?.ifFalseThrow(exception: () -> Exception) = if(this == false) exception.invoke() else this

fun String.replaceLast(toReplace: String, replacement: String): String {
    val pos = this.lastIndexOf(toReplace)
    return if (pos > -1) {
        (this.substring(0, pos)
                + replacement
                + this.substring(pos + toReplace.length))
    } else {
        this
    }
}

fun <A : KClass<out Annotation>> Method.isAnnotatedWithAnyThese(annotations: Collection<A>) = annotations.any() { annotation -> this.isAnnotationPresent(annotation.java) }



