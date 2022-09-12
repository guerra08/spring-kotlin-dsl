package com.guerra08.springkotlindsl.user

import javax.persistence.*

@Entity
@Table(name = "ROLES")
class Role(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val name: String,
    @OneToMany(mappedBy = "role", orphanRemoval = true)
    val users: MutableSet<User> = mutableSetOf()
) {
}

enum class Roles {
    ROLE_USER,
    ROLE_ADMIN
}