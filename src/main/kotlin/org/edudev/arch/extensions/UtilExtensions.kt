package org.edudev.arch.extensions

inline fun <reified T: Any> Any?.takeIfInstance() = this.takeIf { it is T }?.let { it as T }

fun Boolean?.ifFalseThrow(exception: () -> Exception) = if(this == false) exception.invoke() else this




