package com.guerra08.springkotlindsl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinDslApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinDslApplication>(*args) {
        addInitializers(
            beans
        )
    }
}
