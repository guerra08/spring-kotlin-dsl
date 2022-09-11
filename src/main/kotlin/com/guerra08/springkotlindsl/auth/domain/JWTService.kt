package com.guerra08.springkotlindsl.auth.domain

import com.guerra08.springkotlindsl.auth.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import java.time.Instant
import java.util.*

class JWTService {

    @Value("\${app.token.secret}") private lateinit var tokenSecret: String
    @Value("\${app.token.expires_ms}") private lateinit var expiresMs: String

    fun generateTokenForAuth(auth: Authentication): String {
        val user = auth.principal as User
        return generateTokenForUser(user)
    }

    fun generateTokenForUser(user: User): String {
        return Jwts.builder()
            .setIssuer("IRS")
            .setSubject(user.id.toString())
            .setExpiration(Date.from(Instant.now().plusMillis(expiresMs.toLong())))
            .setIssuedAt(Date())
            .signWith(Keys.hmacShaKeyFor(tokenSecret.toByteArray())).compact()
    }

    fun isTokenValid(token: String) =
        try {
            val parser = Jwts.parserBuilder().setSigningKey(tokenSecret.toByteArray()).build()
            parser.parse(token)
            true
        } catch (ex: Exception) {
            false
        }

    fun getTokenId(token: String): Long {
        val parser = Jwts.parserBuilder().setSigningKey(tokenSecret.toByteArray()).build()
        val body = parser.parseClaimsJws(token).body
        return body.subject.toLong()
    }
}