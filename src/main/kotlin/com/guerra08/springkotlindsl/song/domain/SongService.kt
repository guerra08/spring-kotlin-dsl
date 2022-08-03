package com.guerra08.springkotlindsl.song.domain

import com.guerra08.springkotlindsl.song.*
import com.guerra08.springkotlindsl.song.persistence.SongRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SongService(
    private val songRepository: SongRepository
) {

    fun create(songContract: SongContract): SongContract {
        val song = songContract.toSong()
        return songRepository.save(song).toSongContract()
    }

    fun getAll(): List<SongContract> =
        songRepository.findAll().map { it.toSongContract() }

    fun getById(id: Long): SongContract? {
        return songRepository.findByIdOrNull(id)?.toSongContract()
    }

    fun deleteById(id: Long) = when (songRepository.existsById(id)) {
        true -> {
            songRepository.deleteById(id)
            true
        }
        false -> false
    }

    fun putById(id: Long, newSong: SongContract): SongContract? {
        if(songRepository.existsById(id)){
            val songToPut = newSong.toSongWithId(id)
            return songRepository.save(songToPut).toSongContract()
        }
        return null
    }

}