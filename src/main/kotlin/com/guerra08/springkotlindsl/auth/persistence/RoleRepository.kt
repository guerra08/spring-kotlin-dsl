package com.guerra08.springkotlindsl.auth.persistence

import com.guerra08.springkotlindsl.auth.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByName(name: String): Role?

}