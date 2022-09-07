package com.guerra08.springkotlindsl.auth.api

import com.guerra08.springkotlindsl.auth.contract.UserContract
import com.guerra08.springkotlindsl.auth.service.AuthService
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class AuthHandler(
    private val authService: AuthService
) {

    fun signUp(req: ServerRequest): ServerResponse {
        val userContract = req.body(UserContract::class.java)
        authService.signUp(userContract)
        return ServerResponse.ok().build()
    }

    fun signIn(req: ServerRequest): ServerResponse {
        val userContract = req.body(UserContract::class.java)
        val token = authService.signIn(userContract)
        return ServerResponse.ok().body(
            mapOf(
                "prefix" to "Bearer",
                "token" to token
            )
        )
    }

}