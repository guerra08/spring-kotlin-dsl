package com.guerra08.springkotlindsl.unit.domain

import com.guerra08.springkotlindsl.Helpers.generateFakeSong
import com.guerra08.springkotlindsl.Helpers.generateFakeSongContract
import com.guerra08.springkotlindsl.song.Song
import com.guerra08.springkotlindsl.song.domain.SongService
import com.guerra08.springkotlindsl.song.persistence.SongRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class SongServiceTests {

    private val songRepository: SongRepository = mockk()

    private lateinit var sut: SongService

    @BeforeEach
    fun setUp() {
        sut = SongService(songRepository)
    }

    @Test
    fun `given a song contract, create should return the newly created song as song contract`(){
        val songContract = generateFakeSongContract()
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
    fun `get all should return a list of all saved songs, as song contracts`(){

        val songs = listOf(
            generateFakeSong(),
            generateFakeSong()
        )
        every { songRepository.findAll() }.returns(songs)

        val result = sut.getAll()

        result.size shouldBe 2
    }

    @Test
    fun `get by id should return the song contract of the existing song`(){

        val song = generateFakeSong()
        every { songRepository.findByIdOrNull(any()) }.returns(song)

        val result = sut.getById(2L)

        result shouldNotBe null
        result?.name shouldBe song.name
    }

    @Test
    fun `get by id should return null if the song does not exist`(){

        every { songRepository.findByIdOrNull(any()) }.returns(null)

        val result = sut.getById(2L)

        result shouldBe null
    }

    @Test
    fun `delete by id should return true if the song has been deleted`() {

        every { songRepository.existsById(any()) }.returns(true)
        every { songRepository.deleteById(any()) }.returns(Unit)

        val result = sut.deleteById(1L)

        result shouldBe true
    }

    @Test
    fun `delete by id should return false if the song has not been deleted`() {

        every { songRepository.existsById(any()) }.returns(false)

        val result = sut.deleteById(1L)

        result shouldBe false

    }

    @Test
    fun `put by id should return the newly put song, as a song contract`() {

        val toPut = generateFakeSongContract()
        val songInDb = generateFakeSong()

        every { songRepository.existsById(any()) }.returns(true)
        every { songRepository.save(any()) }.returns(songInDb)

        val result = sut.putById(1L, toPut)

        result shouldNotBe null
        result?.name shouldBe songInDb.name

    }

    @Test
    fun `put by id should return null when trying to put a song that does not exist`() {

        val toPut = generateFakeSongContract()

        every { songRepository.existsById(any()) }.returns(false)

        val result = sut.putById(1L, toPut)

        result shouldBe null

    }

}