package org.edudev.arch.extensions

fun <T : Any, A : Annotation> Class<T>.findAnnotationRecursively(annotation: Class<out A>): A? =
    this.getAnnotation(annotation)
        ?: (listOf(this.superclass) + this.interfaces.toMutableList())
            .asSequence()
            .filterNotNull()
            .map { it.findAnnotationRecursively(annotation) }
            .find { it != null }