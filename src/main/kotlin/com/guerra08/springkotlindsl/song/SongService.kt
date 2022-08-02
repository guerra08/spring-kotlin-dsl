package com.guerra08.springkotlindsl.song

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

}