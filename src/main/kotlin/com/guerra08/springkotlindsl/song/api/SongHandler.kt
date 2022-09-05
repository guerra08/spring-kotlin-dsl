package com.guerra08.springkotlindsl.song.api

import com.guerra08.springkotlindsl.song.contract.SongContract
import com.guerra08.springkotlindsl.song.domain.SongService
import com.guerra08.springkotlindsl.song.validation.validateSongContract
import org.springframework.web.server.ServerWebInputException
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class SongHandler(
    private val songService: SongService
) {

    fun index(req: ServerRequest): ServerResponse {
        val songs = songService.getAll()
        return ServerResponse.ok().body(songs)
    }

    fun create(req: ServerRequest): ServerResponse {
        val songContract = req.body(SongContract::class.java)
        validate(songContract)
        songService.create(songContract)
        return ServerResponse.ok().build()
    }

    fun getById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLong()
        val song = songService.getById(id)
        return song?.let {
            ServerResponse.ok().body(it)
        } ?: ServerResponse.notFound().build()
    }

    fun deleteById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLong()
        return when(songService.deleteById(id)) {
            true -> ServerResponse.noContent().build()
            false -> ServerResponse.notFound().build()
        }
    }

    fun putById(req: ServerRequest): ServerResponse {
        val id = req.pathVariable("id").toLong()
        val newSong = req.body(SongContract::class.java)
        validate(newSong)
        val updated = songService.putById(id, newSong)
        return updated?.let {
            ServerResponse.ok().body(it)
        } ?: ServerResponse.notFound().build()
    }

    private fun validate(songContract: SongContract) {
        val validationResult = validateSongContract(songContract)
        if(!validationResult.errors.isEmpty()) {
            throw ServerWebInputException(validationResult.errors.toString())
        }
    }

}