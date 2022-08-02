package com.guerra08.springkotlindsl.song

import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

@Component
class SongHandler(
    private val songService: SongService
) {

    fun index(req: ServerRequest): ServerResponse {
        val songs = songService.getAll()
        return ServerResponse.ok().body(songs)
    }

    fun create(req: ServerRequest): ServerResponse {
        val songContract = req.body(SongContract::class.java)
        songService.create(songContract)
        return ServerResponse.ok().build()
    }

    fun getById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLong()
        val song: SongContract? = songService.getById(id)
        return song?.let {
            ServerResponse.ok().body(it)
        } ?: ServerResponse.notFound().build()
    }

}