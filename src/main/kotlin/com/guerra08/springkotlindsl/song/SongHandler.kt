package com.guerra08.springkotlindsl.song

import com.guerra08.springkotlindsl.song.persistence.SongRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.net.URI

@Component
class SongHandler(
    private val songRepository: SongRepository
) {

    fun index(req: ServerRequest): ServerResponse {
        val songs = songRepository.findAll()
        return ServerResponse.ok().body(songs)
    }

    fun create(req: ServerRequest): ServerResponse {
        val songContract = req.body(SongContract::class.java)
        val createdSong = songRepository.save(songContract.toSong())
        return ServerResponse.created(URI("/song/${createdSong.id}")).build()
    }

    fun getById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLong()
        val song: Song? = songRepository.findByIdOrNull(id)
        return song?.let {
            ServerResponse.ok().body(it)
        } ?: ServerResponse.notFound().build()
    }

}