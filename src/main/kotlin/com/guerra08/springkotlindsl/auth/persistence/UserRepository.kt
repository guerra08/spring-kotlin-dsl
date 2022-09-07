package com.guerra08.springkotlindsl.auth.persistence

import com.guerra08.springkotlindsl.auth.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

}