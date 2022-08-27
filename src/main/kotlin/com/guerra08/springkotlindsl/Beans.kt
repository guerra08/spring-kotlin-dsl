package com.guerra08.springkotlindsl

import com.guerra08.springkotlindsl.song.api.SongHandler
import com.guerra08.springkotlindsl.song.api.SongRoutes
import com.guerra08.springkotlindsl.song.domain.SongService
import org.springframework.context.support.beans

fun beans() = beans {

    //Services
    bean {
        SongService(ref())
    }

    //Handlers
    bean {
        SongHandler(ref())
    }

    //Routes
    bean {
        SongRoutes(ref()).songRouter()
    }

}