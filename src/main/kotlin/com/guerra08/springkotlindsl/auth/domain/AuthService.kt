package com.guerra08.springkotlindsl.auth.domain

import com.guerra08.springkotlindsl.user.contract.UserContract
import com.guerra08.springkotlindsl.user.domain.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JWTService,
    private val userService: UserService,
) {

    fun signUp(userContract: UserContract): String {
        val user = userService.create(userContract)
        return jwtService.generateTokenForUser(user)
    }

    fun signIn(userContract: UserContract): String {
        val upt = UsernamePasswordAuthenticationToken(userContract.email, userContract.password)
        val auth = authenticationManager.authenticate(upt)
        return jwtService.generateTokenForAuth(auth)
    }

}