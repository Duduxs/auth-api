package org.edudev.domain.users


import org.junit.jupiter.api.Assertions.assertEquals

infix fun User.assertEquals(dto: UserDTO){
    assertEquals(this.id, dto.id)
    assertEquals(this.name, dto.name)
    assertEquals(this.username, dto.username)
    assertEquals(this.email, dto.email)
    assertEquals(this.password, dto.password)
    assertEquals(this.profile!!.id, dto.profile.id)
}

infix fun User.assertSummaryEquals(dto: UserSummaryDTO){
    assertEquals(this.id, dto.id)
    assertEquals(this.username, dto.username)
    assertEquals(this.email, dto.email)
}

infix fun Collection<User>.assertCollectionEquals(dto: Collection<UserDTO>){
    val domains = this.sortedByDescending { it.id }
    val dtos = dto.sortedByDescending { it.id }.filter { it.id != "adminId" }

    domains.forEachIndexed { i, user -> user.assertEquals(dtos[i]) }
}

infix fun Collection<User>.assertCollectionSummaryEquals(dto: Collection<UserSummaryDTO>){
    val domains = this.sortedByDescending { it.id }
    val dtos = dto.sortedByDescending { it.id }.filter { it.id != "adminId" }

    domains.forEachIndexed { i, user -> user.assertSummaryEquals(dtos[i]) }
}
