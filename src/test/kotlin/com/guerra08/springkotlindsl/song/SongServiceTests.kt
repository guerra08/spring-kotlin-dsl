package com.guerra08.springkotlindsl.song

import com.guerra08.springkotlindsl.song.persistence.SongRepository
import io.mockk.every
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

}