package com.guerra08.springkotlindsl.song

import com.guerra08.springkotlindsl.song.contract.SongContract
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

fun Song.toSongContract(): SongContract {
    return SongContract(
        name = this.name,
        album = this.album,
        artist = this.artist
    )
}