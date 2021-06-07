package org.edudev.properties

import org.edudev.arch.domain.Entity
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity("Properties")
data class Property(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val address: String,
    @field:NotNull val directionality: Directionality,
    val value: Double
)