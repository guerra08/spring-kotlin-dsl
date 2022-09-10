package com.guerra08.springkotlindsl.song.integration

import com.google.gson.Gson
import com.guerra08.springkotlindsl.song.Helpers.generateFakeSongContract
import com.guerra08.springkotlindsl.song.domain.SongService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.*

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
            generateFakeSongContract()
        )

        client.get("/song")
            .andExpect {
                status { isOk() }
            }

    }

    @Test
    fun getById_shouldReturnOkResponseIfSongExists() {

        every { songService.getById(any()) } returns generateFakeSongContract()

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
    @WithMockUser
    fun post_shouldReturnOkAfterCreatingSong() {

        val songContract = generateFakeSongContract()

        every { songService.create(any()) } returns songContract

        val payload = Gson().toJson(songContract)

        client.post("/song") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

    }

    @Test
    @WithMockUser
    fun putById_shouldReturnOkWhenPutIsSuccessful() {

        val songContract = generateFakeSongContract()

        every { songService.putById(any(), any()) } returns songContract

        val payload = Gson().toJson(songContract)

        client.put("/song/1") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

    }

    @Test
    @WithMockUser
    fun putById_shouldReturnNotFoundIfSongDoesNotExists() {

        val songContract = generateFakeSongContract()

        every { songService.putById(any(), any()) } returns null

        val payload = Gson().toJson(songContract)

        client.put("/song/1") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }

    }

    @Test
    @WithMockUser
    fun deleteById_shouldReturnNoContentIfDeleteIsSuccessful() {

        every { songService.deleteById(any()) } returns true

        client.delete("/song/1")
            .andExpect {
                status { isNoContent() }
            }

    }

    @Test
    @WithMockUser
    fun deleteById_shouldReturnNotFound() {

        every { songService.deleteById(any()) } returns false

        client.delete("/song/1")
            .andExpect {
                status { isNotFound() }
            }

    }

}