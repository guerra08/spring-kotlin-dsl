package com.guerra08.springkotlindsl.song

import javax.persistence.*

@Entity
@Table(name = "SONGS")
class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val name: String,
    val album: String,
    val artist: String
)

// TODO: Map id to HASH
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

fun Song.toSongContract(): SongContract {
    return SongContract(
        name = this.name,
        album = this.album,
        artist = this.artist
    )
}