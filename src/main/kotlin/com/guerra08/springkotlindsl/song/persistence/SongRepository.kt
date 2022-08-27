package com.guerra08.springkotlindsl.song.persistence

import com.guerra08.springkotlindsl.song.Song
import org.springframework.data.jpa.repository.JpaRepository

interface SongRepository: JpaRepository<Song, Long> { }