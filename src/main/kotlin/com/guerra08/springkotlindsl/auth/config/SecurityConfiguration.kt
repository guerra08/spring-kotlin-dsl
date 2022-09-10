package com.guerra08.springkotlindsl.auth.config

import com.guerra08.springkotlindsl.auth.filter.JWTFilter
import com.guerra08.springkotlindsl.auth.persistence.UserRepository
import com.guerra08.springkotlindsl.auth.domain.JWTService
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

fun securityFilterChain(
    http: HttpSecurity,
    jwtService: JWTService,
    userRepository: UserRepository
): SecurityFilterChain {
    http {
        cors { }
        csrf {
            disable()
        }
        sessionManagement {
            sessionCreationPolicy = SessionCreationPolicy.STATELESS
        }
        authorizeRequests {
            authorize("/auth/**", permitAll)
            authorize(HttpMethod.GET, "/song/**", permitAll)
            authorize(anyRequest, authenticated)
        }
        addFilterBefore<UsernamePasswordAuthenticationFilter>(JWTFilter(jwtService, userRepository))
    }
    return http.build()
}