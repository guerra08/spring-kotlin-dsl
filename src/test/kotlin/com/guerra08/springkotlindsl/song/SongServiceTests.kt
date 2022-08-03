package com.guerra08.springkotlindsl.song

import com.guerra08.springkotlindsl.song.domain.SongService
import com.guerra08.springkotlindsl.song.persistence.SongRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class SongServiceTests {

    private val songRepository: SongRepository = mockk()
    private val sut: SongService = SongService(songRepository)

    @Test
    fun create_shouldReturnCreatedSongContract(){
        val songContract = SongContract(
            name = "Aerials",
            album = "Toxicity",
            artist = "SOAD"
        )
        val createdSong = Song(
            id = 1L,
            name = songContract.name,
            album = songContract.album,
            artist = songContract.artist
        )
        every { songRepository.save(any()) }.returns(createdSong)

        val result = sut.create(songContract)

        Assertions.assertEquals(songContract.name, result.name)
        Assertions.assertEquals(songContract.album, result.album)
        Assertions.assertEquals(songContract.artist, result.artist)
    }

    @Test
    fun getAll_shouldReturnListOfMappedSongsToSongContract(){

        val songs = listOf(
            Song(
                id = 1L,
                name = "Du Hast",
                album = "Sehnsucht",
                artist = "Rammstein"
            ),
            Song(
                id = 2L,
                name = "Smooth Sailing",
                album = "Like Clockwork...",
                artist = "QOTSA"
            )
        )
        every { songRepository.findAll() }.returns(songs)

        val result = sut.getAll()

        Assertions.assertEquals(2, result.size)
    }

    @Test
    fun getById_shouldReturnSongContractIfSongExists(){

        val song = Song(
            id = 2L,
            name = "Smooth Sailing",
            album = "Like Clockwork...",
            artist = "QOTSA"
        )
        every { songRepository.findByIdOrNull(any()) }.returns(song)

        val result = sut.getById(2L)

        Assertions.assertNotNull(result)
        Assertions.assertEquals("Smooth Sailing", result?.name)

    }

    @Test
    fun getById_shouldReturnNullIfSongDoesNotExist(){

        every { songRepository.findByIdOrNull(any()) }.returns(null)

        val result = sut.getById(2L)

        Assertions.assertNull(result)

    }

    @Test
    fun deleteById_shouldReturnTrueIfDeletesExistingSong() {

        every { songRepository.existsById(any()) }.returns(true)
        every { songRepository.deleteById(any()) }.returns(Unit)

        val result = sut.deleteById(1L)

        Assertions.assertTrue(result)

    }

    @Test
    fun deleteById_shouldReturnFalseIfSongDoesNotExist() {

        every { songRepository.existsById(any()) }.returns(false)

        val result = sut.deleteById(1L)

        Assertions.assertFalse(result)

    }

    @Test
    fun putById_shouldReturnNewlyPutSongAsContract() {

        val toPut = SongContract("Un-reborn Again", "Villains", "QOTSA")
        val songInDb = Song(1L, "Un-reborn Again", "Villains", "QOTSA")

        every { songRepository.existsById(any()) }.returns(true)
        every { songRepository.save(any()) }.returns(songInDb)

        val result = sut.putById(1L, toPut)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(songInDb.name, result?.name)
        Assertions.assertEquals(toPut.name, result?.name)

    }

    @Test
    fun putById_shouldReturnNullIfSongDoesNotExist() {

        val toPut = SongContract("Un-reborn Again", "Villains", "QOTSA")

        every { songRepository.existsById(any()) }.returns(false)

        val result = sut.putById(1L, toPut)

        Assertions.assertNull(result)

    }

}