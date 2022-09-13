package com.guerra08.springkotlindsl.unit.domain

import com.guerra08.springkotlindsl.user.User
import com.guerra08.springkotlindsl.auth.domain.AuthService
import com.guerra08.springkotlindsl.auth.domain.JWTService
import com.guerra08.springkotlindsl.user.domain.UserService
import com.guerra08.springkotlindsl.Helpers.generateFakeUserContract
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class AuthServiceTests {

    private val authenticationManager: AuthenticationManager = mockk()
    private val jwtService: JWTService = mockk()
    private val userService: UserService = mockk()

    private lateinit var sut: AuthService

    @BeforeEach
    fun setUp() {
        sut = AuthService(authenticationManager, jwtService, userService)
    }

    @Test
    fun `given sign up with user contract, should create user and return jwt token`() {

        val userContract = generateFakeUserContract()
        val user = User(email = userContract.email, password = userContract.password)
        val token = "abc123def456"

        every { userService.create(any()) } returns user
        every { jwtService.generateTokenForUser(any()) } returns token

        val result = sut.signUp(userContract)

        verify {
            userService.create(userContract)
            jwtService.generateTokenForUser(user)
        }

        result shouldBe token
    }

    @Test
    fun `given sign in with user contract, should return jwt token if authentication succeeds`() {

        val userContract = generateFakeUserContract()
        val token = "123abc456def"

        every { authenticationManager.authenticate(any()) } returns UsernamePasswordAuthenticationToken(userContract.email, userContract.password)
        every { jwtService.generateTokenForAuth(any()) } returns token

        val result = sut.signIn(userContract)

        verify {
            authenticationManager.authenticate(any())
            jwtService.generateTokenForAuth(any())
        }

        result shouldBe token
    }

    @Test
    fun `given invalid credentials, should throw and exception and not return the token`() {

        val userContract = generateFakeUserContract()

        every { authenticationManager.authenticate(any()) } throws BadCredentialsException("Invalid credentials")

        assertThrows<BadCredentialsException> {
            sut.signIn(userContract
            )
        }
    }
    //TODO: Create tests for UserService and JWTService

}