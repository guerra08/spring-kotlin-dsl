package com.guerra08.springkotlindsl

import com.guerra08.springkotlindsl.auth.api.AuthHandler
import com.guerra08.springkotlindsl.auth.api.AuthRoutes
import com.guerra08.springkotlindsl.auth.config.securityFilterChain
import com.guerra08.springkotlindsl.auth.domain.AuthService
import com.guerra08.springkotlindsl.auth.domain.JWTService
import com.guerra08.springkotlindsl.auth.domain.UserService
import com.guerra08.springkotlindsl.auth.seeder.rolesUsersSeeder
import com.guerra08.springkotlindsl.song.api.SongHandler
import com.guerra08.springkotlindsl.song.api.SongRoutes
import com.guerra08.springkotlindsl.song.domain.SongService
import org.springframework.context.support.beans
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

fun beans() = beans {

    //Services
    bean {
        SongService(ref())
    }
    bean {
        UserService(ref(), ref(), ref())
    }
    bean {
        AuthService(ref(), ref(), ref())
    }
    bean {
        JWTService()
    }

    //Handlers
    bean {
        SongHandler(ref())
    }
    bean {
        AuthHandler(ref())
    }

    //Routes
    bean {
        SongRoutes(ref()).songRouter()
    }
    bean {
        AuthRoutes(ref()).authRouter()
    }

    //Security
    bean<PasswordEncoder> {
        BCryptPasswordEncoder()
    }
    bean<AuthenticationManager> {
        ref<AuthenticationConfiguration>().authenticationManager
    }
    bean {
        securityFilterChain(ref(), ref(), ref())
    }

    //Seeders
    bean {
        rolesUsersSeeder(ref(), ref(), ref())
    }

}