package com.guerra08.springkotlindsl.user.persistence

import com.guerra08.springkotlindsl.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

}