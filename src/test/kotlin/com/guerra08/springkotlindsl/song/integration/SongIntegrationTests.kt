package com.guerra08.springkotlindsl.song.integration

import com.google.gson.Gson
import com.guerra08.springkotlindsl.song.SongContract
import com.guerra08.springkotlindsl.song.domain.SongService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class SongIntegrationTests(
    @Autowired private val client: MockMvc
) {

    @MockkBean
    private lateinit var songService: SongService

    @Test
    fun getIndex_shouldReturnOkResponseWithSongs() {

        every { songService.getAll() } returns listOf(
            SongContract(name = "Hurt", album = "Downward Spiral", artist = "NIN")
        )

        client.get("/song")
            .andExpect {
                status { isOk() }
            }

    }

    @Test
    fun getById_shouldReturnOkResponseIfSongExists() {

        every { songService.getById(any()) } returns SongContract(name = "Hurt", album = "Downward Spiral", artist = "NIN")


        client.get("/song/{id}", 1L)
            .andExpect {
                status { isOk() }
            }

    }

    @Test
    fun getById_shouldReturnNotFoundResponseIfSongDoesNotExist() {

        every { songService.getById(any()) } returns null


        client.get("/song/{id}", 1L)
            .andExpect {
                status { isNotFound() }
            }

    }

    @Test
    fun post_shouldReturnOkAfterCreatingSong() {

        val song = SongContract(name = "Hurt", album = "Downward Spiral", artist = "NIN")

        every { songService.create(any()) } returns song

        val payload = Gson().toJson(song)

        client.post("/song") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

    }

    //TODO: Add missing tests

}