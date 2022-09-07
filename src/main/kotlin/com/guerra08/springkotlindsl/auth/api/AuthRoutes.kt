package com.guerra08.springkotlindsl.auth.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

class AuthRoutes(
    private val authHandler: AuthHandler
) {

    fun authRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/auth").nest {
            POST("/signUp", authHandler::signUp)
            POST("/signIn", authHandler::signIn)
        }
    }

}