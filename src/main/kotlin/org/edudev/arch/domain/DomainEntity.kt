package org.edudev.arch.domain

import java.io.Serializable

interface DomainEntity : Serializable{
    val _id: String
}