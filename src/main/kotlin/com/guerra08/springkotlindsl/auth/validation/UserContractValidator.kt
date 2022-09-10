package com.guerra08.springkotlindsl.auth.validation

import com.guerra08.springkotlindsl.auth.contract.UserContract
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern

val validateUserContract = Validation {
    val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    UserContract::email required {
        pattern(emailRegex)
    }
    UserContract::password required {
        minLength(5)
        maxLength(20)
    }
}

