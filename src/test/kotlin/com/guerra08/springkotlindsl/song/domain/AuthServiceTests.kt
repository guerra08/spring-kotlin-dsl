package com.guerra08.springkotlindsl.song.domain

import com.guerra08.springkotlindsl.auth.User
import com.guerra08.springkotlindsl.auth.domain.AuthService
import com.guerra08.springkotlindsl.auth.domain.JWTService
import com.guerra08.springkotlindsl.auth.domain.UserService
import com.guerra08.springkotlindsl.song.Helpers.generateFakeUserContract
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.AuthenticationManager

class AuthServiceTests {

    private val authenticationManager: AuthenticationManager = mockk()
    private val jwtService: JWTService = mockk()
    private val userService: UserService = mockk()

    private val sut = AuthService(authenticationManager, jwtService, userService)

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

}