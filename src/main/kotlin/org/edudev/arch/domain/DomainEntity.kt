package org.edudev.arch.domain

import java.io.Serializable

interface DomainEntity : Serializable {
    val id: String
}