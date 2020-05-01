package com.arctouch.codechallenge.data.source.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        primaryKeys = ["movie_id", "genre_id"], foreignKeys = [
            ForeignKey(
                    entity = MovieDb::class,
                    parentColumns = ["_movie_id"],
                    childColumns = ["movie_id"],
                    onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                    entity = GenreDb::class,
                    parentColumns = ["_genre_id"],
                    childColumns = ["genre_id"],
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
data class MovieGenreDb (
    val movie_id : Long,
    val genre_id : Long
)