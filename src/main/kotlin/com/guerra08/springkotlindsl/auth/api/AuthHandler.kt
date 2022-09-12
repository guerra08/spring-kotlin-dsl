package com.guerra08.springkotlindsl.auth.api

import com.guerra08.springkotlindsl.user.contract.UserContract
import com.guerra08.springkotlindsl.auth.domain.AuthService
import com.guerra08.springkotlindsl.user.validation.validateUserContract
import org.springframework.web.server.ServerWebInputException
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class AuthHandler(
    private val authService: AuthService
) {

    fun signUp(req: ServerRequest): ServerResponse {
        val userContract = req.body(UserContract::class.java)
        validate(userContract)
        authService.signUp(userContract)
        return ServerResponse.ok().build()
    }

    fun signIn(req: ServerRequest): ServerResponse {
        val userContract = req.body(UserContract::class.java)
        validate(userContract)
        val token = authService.signIn(userContract)
        return ServerResponse.ok().body(
            mapOf(
                "prefix" to "Bearer",
                "token" to token
            )
        )
    }

    private fun validate(contract: UserContract) {
        val validationResult = validateUserContract(contract)
        if(!validationResult.errors.isEmpty()) {
            throw ServerWebInputException(validationResult.errors.toString())
        }
    }

}