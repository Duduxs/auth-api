package org.edudev.arch.extensions

import java.util.*

private const val basicAuthorizationPrefix = "Basic"

fun String.decodeBase64Authorization() = this
    .removePrefix(basicAuthorizationPrefix)
    .trim()
    .decodeAsStringTokenizer()

fun String.decodeAsStringTokenizer() = Base64.getDecoder().decode(this).toStringTokenizer()

fun ByteArray.toStringTokenizer() = StringTokenizer(String(this), ":")
