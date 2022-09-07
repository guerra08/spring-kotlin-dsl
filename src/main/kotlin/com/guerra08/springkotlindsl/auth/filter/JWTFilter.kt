package com.guerra08.springkotlindsl.auth.filter

import com.guerra08.springkotlindsl.auth.persistence.UserRepository
import com.guerra08.springkotlindsl.auth.service.JWTService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTFilter(
    private val jwtService: JWTService,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getTokenFromHeader(request)
        token?.let {
            authenticate(it)
        }
        filterChain.doFilter(request, response)
    }

    private fun authenticate(token: String) {
        val id = jwtService.getTokenId(token)
        userRepository.findByIdOrNull(id)?.let {
            val upt = UsernamePasswordAuthenticationToken(it, null, null)
            SecurityContextHolder.getContext().authentication = upt
        }
    }

    private fun getTokenFromHeader(request: HttpServletRequest): String? {
        val token: String? = request.getHeader("Authorization")
        return token?.let {
            if(it.startsWith("Bearer "))
                it.substring(7)
            else null
        }
    }
}