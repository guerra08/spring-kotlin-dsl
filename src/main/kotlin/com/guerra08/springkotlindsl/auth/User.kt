package com.guerra08.springkotlindsl.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email

@Entity
@Table(name = "USERS")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @field:Email
    val email: String,
    private val password: String

) : UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf()

    override fun getPassword() = password

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}