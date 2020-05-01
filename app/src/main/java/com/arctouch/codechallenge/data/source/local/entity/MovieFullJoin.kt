package com.arctouch.codechallenge.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class MovieFullJoin {
    @Embedded
    lateinit var movie : MovieDb

    @Relation(
            parentColumn = "_movie_id",
            entity = GenreDb::class,
            entityColumn = "_genre_id",
            associateBy = Junction(
                    value = MovieGenreDb::class,
                    parentColumn = "movie_id",
                    entityColumn = "genre_id"
            )
    )
    lateinit var genres : List<GenreDb>
}