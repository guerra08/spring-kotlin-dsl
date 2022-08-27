package com.guerra08.springkotlindsl.song.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

class SongRoutes(
    private val songHandler: SongHandler
) {

    fun songRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/song").nest {
            GET("", songHandler::index)
            POST("", songHandler::create)
            GET("/{id}", songHandler::getById)
            PUT("/{id}", songHandler::putById)
            DELETE("/{id}", songHandler::deleteById)
        }
    }

}