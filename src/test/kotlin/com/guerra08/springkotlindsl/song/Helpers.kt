package com.guerra08.springkotlindsl.song

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

}


