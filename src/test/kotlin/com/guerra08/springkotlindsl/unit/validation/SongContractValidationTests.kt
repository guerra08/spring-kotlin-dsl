package com.guerra08.springkotlindsl.unit.validation

import com.guerra08.springkotlindsl.song.contract.SongContract
import com.guerra08.springkotlindsl.song.validation.validateSongContract
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SongContractValidationTests {

    @Test
    fun `given a valid song contract, validateSongContract should not return errors`() {

        val contract = SongContract(name = "Song", album = "Album", artist = "Artist")

        val result = validateSongContract(contract)

        result.errors.size shouldBe 0

    }

    @Test
    fun `given invalid song contract, validateSongContract should return errors`() {

        val contract = SongContract(name = "m9RQLF8TETe7WRZe7sNoY8Z9UuOcPycHTPlbioIJwV8nk9DCkEWzt4bgVUcoQ3kA55tWqDOvkHrwNDCuZZqExoewPBhW4oL5helSs", album = "Album", artist = "Artist")

        val result = validateSongContract(contract)

        result.errors.size shouldBe 1

    }

}