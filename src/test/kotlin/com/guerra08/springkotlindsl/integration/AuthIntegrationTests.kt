package com.guerra08.springkotlindsl.integration

import com.guerra08.springkotlindsl.Helpers.generateFakeUserContract
import com.guerra08.springkotlindsl.Helpers.generateInvalidUserContract
import com.guerra08.springkotlindsl.auth.domain.AuthService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTests(
    @Autowired private val client: MockMvc
) {

    @MockkBean
    private lateinit var authService: AuthService

    @Test
    fun `given invalid user contract, signUp should return bad request`() {

        val payload = generateInvalidUserContract().let {
            Json.encodeToString(it)
        }

        client.post("/auth/signUp") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }

    }

    @Test
    fun `given invalid user contract, signIn should return bad request`() {

        val payload = generateInvalidUserContract().let {
            Json.encodeToString(it)
        }

        client.post("/auth/signIn") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }

    }

    @Test
    fun `given valid and correct user contract, signUp should return generated token for created user`() {

        val payload = generateFakeUserContract().let {
            Json.encodeToString(it)
        }

        val token = "abc123def456"

        every { authService.signUp(any()) } returns token

        client.post("/auth/signUp") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

    }

    @Test
    fun `given valid and correct user contract, signIn should return token for user`() {

        val payload = generateFakeUserContract().let {
            Json.encodeToString(it)
        }

        val token = "abc123def456"

        every { authService.signIn(any()) } returns token

        client.post("/auth/signIn") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

    }

}