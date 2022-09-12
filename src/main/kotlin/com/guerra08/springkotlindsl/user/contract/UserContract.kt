package com.guerra08.springkotlindsl.user.contract

import com.guerra08.springkotlindsl.user.User

data class UserContract(val email: String, val password: String)

fun UserContract.toUser(encryptedPassword: String) =
    User(email = this.email, password = encryptedPassword)
