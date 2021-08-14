package org.edudev.arch.extensions

import org.edudev.arch.auth.Restricted
import org.edudev.arch.auth.functionality.GlobalFunctionality
import org.edudev.arch.auth.functionality.action.CrudAction
import org.edudev.arch.exceptions.ForbiddenHttpException
import org.edudev.domain.users.User
import java.lang.reflect.Method
import java.util.*

private const val basicAuthorizationPrefix = "Basic"

fun String.decodeBase64Authorization() = this
    .removePrefix(basicAuthorizationPrefix)
    .trim()
    .decodeAsStringTokenizer()

fun String.decodeAsStringTokenizer() = Base64.getDecoder().decode(this).toStringTokenizer()

fun ByteArray.toStringTokenizer() = StringTokenizer(String(this), ":")

fun Restricted?.isNullOrEmpty() = this == null || this.functionality == GlobalFunctionality.EMPTY

fun Boolean?.orThrowForbidden(
    currentUser: User,
    crudAction: CrudAction,
    functionality: GlobalFunctionality
) = this.ifFalseThrow { throw ForbiddenHttpException("O perfil de ${currentUser.username} não possui permissão de $crudAction a $functionality") }
