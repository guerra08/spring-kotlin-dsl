package com.guerra08.springkotlindsl.song.api

import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.router

@Component
class SongRoutes(
    private val songHandler: SongHandler
) {

    @Bean
    fun songRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/song").nest {
            GET("", songHandler::index)
            GET("/{id}", songHandler::getById)
            POST("", songHandler::create)
            PUT("/{id}", songHandler::putById)
            DELETE("/{id}", songHandler::deleteById)
        }
    }

}