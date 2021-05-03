package org.edudev.domain.states

import java.util.*

data class State (
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val habitats: Int,
    val region: Region,
)