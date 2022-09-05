package com.guerra08.springkotlindsl.song.validation

import com.guerra08.springkotlindsl.song.contract.SongContract
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.Validation

val validateSongContract = Validation<SongContract> {
    SongContract::name required {
        maxLength(100)
    }
    SongContract::album required {
        maxLength(100)
    }
    SongContract::artist required {
        maxLength(100)
    }
}