package com.guerra08.springkotlindsl.user.domain

import com.guerra08.springkotlindsl.user.Role
import com.guerra08.springkotlindsl.user.Roles
import com.guerra08.springkotlindsl.user.User
import com.guerra08.springkotlindsl.user.contract.UserContract
import com.guerra08.springkotlindsl.user.contract.toUser
import com.guerra08.springkotlindsl.user.persistence.RoleRepository
import com.guerra08.springkotlindsl.user.persistence.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

class UserService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun create(userContract: UserContract): User {
        return roleRepository.findByName(Roles.ROLE_USER.name)?.let {
            create(userContract, it)
        } ?: throw IllegalStateException()
    }

    private fun create(userContract: UserContract, role: Role): User {
        val user = userContract.toUser(passwordEncoder.encode(userContract.password)).also {
            it.role = role
        }
        return userRepository.save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User with given email not found.")
    }

}