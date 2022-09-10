package com.guerra08.springkotlindsl.auth.domain

import com.guerra08.springkotlindsl.auth.contract.UserContract
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JWTService,
    private val userService: UserService,
) {

    fun signUp(userContract: UserContract): String {
        val user = userService.create(userContract)
        return jwtService.generateToken(user)
    }

    fun signIn(userContract: UserContract): String {
        val upt = UsernamePasswordAuthenticationToken(userContract.email, userContract.password)
        val auth = authenticationManager.authenticate(upt)
        return jwtService.generateToken(auth)
    }

}