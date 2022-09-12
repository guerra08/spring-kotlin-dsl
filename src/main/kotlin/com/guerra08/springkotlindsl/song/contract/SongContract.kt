package com.guerra08.springkotlindsl.song.contract

import com.guerra08.springkotlindsl.song.Song
import kotlinx.serialization.Serializable

@Serializable
data class SongContract(
    val name: String,
    val album: String,
    val artist: String
)

fun SongContract.toSong(): Song {
    return Song(
        name = this.name,
        album = this.album,
        artist = this.artist
    )
}

fun SongContract.toSongWithId(id: Long): Song {
    return Song(
        id = id,
        name = this.name,
        album = this.album,
        artist = this.artist
    )
}