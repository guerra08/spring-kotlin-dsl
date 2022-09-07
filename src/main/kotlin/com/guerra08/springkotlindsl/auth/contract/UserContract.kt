package com.guerra08.springkotlindsl.auth.contract

import com.guerra08.springkotlindsl.auth.User

data class UserContract(val email: String, val password: String)

fun UserContract.toUser(encryptedPassword: String) =
    User(email = this.email, password = encryptedPassword)
