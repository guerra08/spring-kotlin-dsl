package com.guerra08.springkotlindsl.song.domain

import com.guerra08.springkotlindsl.song.Helpers.generateFakeSong
import com.guerra08.springkotlindsl.song.Helpers.generateFakeSongContract
import com.guerra08.springkotlindsl.song.Song
import com.guerra08.springkotlindsl.song.persistence.SongRepository
import io.github.serpro69.kfaker.Faker
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class SongServiceTests {

    private val songRepository: SongRepository = mockk()
    private val faker = Faker()
    private val sut: SongService = SongService(songRepository)

    @Test
    fun create_shouldReturnCreatedSongContract(){
        val songContract = generateFakeSongContract();
        val createdSong = Song(
            id = 1L,
            name = songContract.name,
            album = songContract.album,
            artist = songContract.artist
        )
        every { songRepository.save(any()) }.returns(createdSong)

        val result = sut.create(songContract)

        result.name shouldBe songContract.name
        result.album shouldBe songContract.album
        result.artist shouldBe songContract.artist
    }

    @Test
    fun getAll_shouldReturnListOfMappedSongsToSongContract(){

        val songs = listOf(
            generateFakeSong(),
            generateFakeSong()
        )
        every { songRepository.findAll() }.returns(songs)

        val result = sut.getAll()

        result.size shouldBe 2
    }

    @Test
    fun getById_shouldReturnSongContractIfSongExists(){

        val song = generateFakeSong()
        every { songRepository.findByIdOrNull(any()) }.returns(song)

        val result = sut.getById(2L)

        result shouldNotBe null
        result?.name shouldBe song.name
    }

    @Test
    fun getById_shouldReturnNullIfSongDoesNotExist(){

        every { songRepository.findByIdOrNull(any()) }.returns(null)

        val result = sut.getById(2L)

        result shouldBe null
    }

    @Test
    fun deleteById_shouldReturnTrueIfDeletesExistingSong() {

        every { songRepository.existsById(any()) }.returns(true)
        every { songRepository.deleteById(any()) }.returns(Unit)

        val result = sut.deleteById(1L)

        result shouldBe true
    }

    @Test
    fun deleteById_shouldReturnFalseIfSongDoesNotExist() {

        every { songRepository.existsById(any()) }.returns(false)

        val result = sut.deleteById(1L)

        result shouldBe false

    }

    @Test
    fun putById_shouldReturnNewlyPutSongAsContract() {

        val toPut = generateFakeSongContract()
        val songInDb = generateFakeSong()

        every { songRepository.existsById(any()) }.returns(true)
        every { songRepository.save(any()) }.returns(songInDb)

        val result = sut.putById(1L, toPut)

        result shouldNotBe null
        result?.name shouldBe songInDb.name

    }

    @Test
    fun putById_shouldReturnNullIfSongDoesNotExist() {

        val toPut = generateFakeSongContract()

        every { songRepository.existsById(any()) }.returns(false)

        val result = sut.putById(1L, toPut)

        result shouldBe null

    }

}