package com.guerra08.springkotlindsl.integration

import com.guerra08.springkotlindsl.Helpers.generateFakeSongContract
import com.guerra08.springkotlindsl.song.domain.SongService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
    fun `get all songs should return a list of all songs, without requiring authentication`() {

        every { songService.getAll() } returns listOf(
            generateFakeSongContract()
        )

        client.get("/song")
            .andExpect {
                status { isOk() }
            }

    }

    @Test
    fun `get a song by id should return the song, without requiring authentication`() {

        every { songService.getById(any()) } returns generateFakeSongContract()

        client.get("/song/{id}", 1L)
            .andExpect {
                status { isOk() }
            }

    }

    @Test
    fun `get by id should return not found if song does not exist, without requiring authentication`() {

        every { songService.getById(any()) } returns null


        client.get("/song/{id}", 1L)
            .andExpect {
                status { isNotFound() }
            }

    }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `post new song should return the created song, requiring authentication`() {

        val songContract = generateFakeSongContract()

        every { songService.create(any()) } returns songContract

        val payload = Json.encodeToString(value = songContract)

        client.post("/song") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

    }

    @Test
    @WithMockUser
    fun `put song by id should return the new song, requiring authentication`() {

        val songContract = generateFakeSongContract()

        every { songService.putById(any(), any()) } returns songContract

        val payload = Json.encodeToString(value = songContract)

        client.put("/song/1") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }

    }

    @Test
    @WithMockUser
    fun `put song by id should return not found if song does not exist, requiring authentication`() {

        val songContract = generateFakeSongContract()

        every { songService.putById(any(), any()) } returns null

        val payload = Json.encodeToString(value = songContract)

        client.put("/song/1") {
            content = payload
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }

    }

    @Test
    @WithMockUser
    fun `delete song by id should return no content if song is deleted, requiring authentication`() {

        every { songService.deleteById(any()) } returns true

        client.delete("/song/1")
            .andExpect {
                status { isNoContent() }
            }

    }

    @Test
    @WithMockUser
    fun `delete by id should return not found if song does not exist, requiring authentication`() {

        every { songService.deleteById(any()) } returns false

        client.delete("/song/1")
            .andExpect {
                status { isNotFound() }
            }

    }

}