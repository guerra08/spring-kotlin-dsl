package com.guerra08.springkotlindsl.seeder

import com.guerra08.springkotlindsl.user.Role
import com.guerra08.springkotlindsl.user.Roles
import com.guerra08.springkotlindsl.user.User
import com.guerra08.springkotlindsl.user.persistence.RoleRepository
import com.guerra08.springkotlindsl.user.persistence.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder

fun rolesUsersSeeder(
    roleRepository: RoleRepository,
    userRepository: UserRepository,
    passwordEncoder: PasswordEncoder
) = CommandLineRunner {
    val userRole = Role(name = Roles.ROLE_USER.name)
    val adminRole = Role(name = Roles.ROLE_ADMIN.name)

    roleRepository.saveAll(setOf(userRole, adminRole))

    val admin = User(
        email = "admin@email.com",
        password = passwordEncoder.encode("12345"),
        role = adminRole
    )

    userRepository.save(admin)
}