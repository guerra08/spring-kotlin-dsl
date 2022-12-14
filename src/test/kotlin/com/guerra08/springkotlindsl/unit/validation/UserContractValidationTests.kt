package com.guerra08.springkotlindsl.unit.validation

import com.guerra08.springkotlindsl.user.contract.UserContract
import com.guerra08.springkotlindsl.user.validation.validateUserContract
import com.guerra08.springkotlindsl.Helpers.generateFakeUserContract
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UserContractValidationTests {

    @Test
    fun `given valid user contract, should not return validation errors`() {

        val userContract = generateFakeUserContract()

        val result = validateUserContract(userContract)

        result.errors.size shouldBe 0

    }

    @Test
    fun `given an invalid user contract, should return populated validation errors`() {

        val userContract = UserContract(email = "invalid@.com", password = "123")

        val result = validateUserContract(userContract)

        result.errors.size shouldBe 2

    }

}