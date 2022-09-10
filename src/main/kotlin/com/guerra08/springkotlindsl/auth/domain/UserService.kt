package com.guerra08.springkotlindsl.auth.domain

import com.guerra08.springkotlindsl.auth.User
import com.guerra08.springkotlindsl.auth.contract.UserContract
import com.guerra08.springkotlindsl.auth.contract.toUser
import com.guerra08.springkotlindsl.auth.persistence.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun create(userContract: UserContract): User {
        val toSave = userContract.toUser(passwordEncoder.encode(userContract.password))
        return userRepository.save(toSave)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User with given email not found.")
    }

}