package com.guerra08.springkotlindsl.song

import com.guerra08.springkotlindsl.song.contract.SongContract
import org.hibernate.validator.constraints.Length
import javax.persistence.*

@Entity
@Table(name = "SONGS")
class Song(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @field:Length(max = 100)
    val name: String,
    @field:Length(max = 100)
    val album: String,
    @field:Length(max = 100)
    val artist: String
)

fun Song.toSongContract(): SongContract {
    return SongContract(
        name = this.name,
        album = this.album,
        artist = this.artist
    )
}