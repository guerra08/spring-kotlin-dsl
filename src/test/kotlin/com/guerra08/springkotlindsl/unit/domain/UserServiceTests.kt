package com.guerra08.springkotlindsl.unit.domain

import com.guerra08.springkotlindsl.Helpers.generateFakeUserContract
import com.guerra08.springkotlindsl.user.Role
import com.guerra08.springkotlindsl.user.Roles
import com.guerra08.springkotlindsl.user.User
import com.guerra08.springkotlindsl.user.domain.UserService
import com.guerra08.springkotlindsl.user.persistence.RoleRepository
import com.guerra08.springkotlindsl.user.persistence.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTests {

    private val roleRepository: RoleRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    private lateinit var sut: UserService

    @BeforeEach
    fun setUp() {
        sut = UserService(roleRepository, userRepository, passwordEncoder)
    }

    @Test
    fun `create should return newly created user`() {

        val userContract = generateFakeUserContract()
        val role = Role(id = 1L, name = Roles.ROLE_USER.name)
        val user = User(id = 1L, email = userContract.email, password = "encodedPassword", role = role)

        every { roleRepository.findByName(any()) } returns role
        every { userRepository.save(any()) } returns user

        val result = sut.create(userContract)

        verify {
            roleRepository.findByName(any())
            userRepository.save(any())
        }

        result.id shouldBe 1L
        result.email shouldBe userContract.email

    }

    @Test
    fun `create should throw IllegalStateException if default role is not found`() {
        val userContract = generateFakeUserContract()

        every { roleRepository.findByName(any()) } throws IllegalStateException()

        assertThrows<IllegalStateException> { sut.create(userContract) }
    }

    @Test
    fun `load by username should return the user as user details if it exists`() {

        val user = User(id = 1L, email = "email@email.com", password = "secret123")

        every { userRepository.findByEmail(any()) } returns user

        val result = sut.loadUserByUsername(user.email)

        verify { userRepository.findByEmail(any()) }

        result.username shouldBe user.email

    }

    @Test
    fun `load by username should throw UsernameNotFoundException if user does not exist`() {

        every { userRepository.findByEmail(any()) } returns null

        assertThrows<UsernameNotFoundException> { sut.loadUserByUsername("email@email.com") }

    }

}