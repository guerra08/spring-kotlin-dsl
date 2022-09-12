package com.guerra08.springkotlindsl.user.persistence

import com.guerra08.springkotlindsl.user.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByName(name: String): Role?

}