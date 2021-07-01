package org.edudev.arch.extensions

import java.util.*

fun String.decodeAsStringTokenizer() = String(Base64.getDecoder().decode(this)).toStringTokenizer()

fun String.toStringTokenizer() = StringTokenizer(this, ":")