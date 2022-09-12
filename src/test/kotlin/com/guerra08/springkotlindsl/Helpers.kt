package com.guerra08.springkotlindsl

import com.guerra08.springkotlindsl.song.Song
import com.guerra08.springkotlindsl.user.contract.UserContract
import com.guerra08.springkotlindsl.song.contract.SongContract
import io.github.serpro69.kfaker.Faker
import kotlin.random.Random

object Helpers {

    private val faker = Faker()

    fun generateFakeSongContract(): SongContract {
        return SongContract(
            name = faker.lorem.words(),
            album = faker.music.albums(),
            artist = faker.music.bands()
        )
    }

    fun generateFakeSong(): Song {
        return Song(
            id = Random.nextLong(),
            name = faker.lorem.words(),
            album = faker.music.albums(),
            artist = faker.music.bands()
        )
    }

    fun generateFakeUserContract(): UserContract {
        return UserContract(
            email = faker.internet.email(),
            password = faker.random.randomString(length = 13)
        )
    }

}


