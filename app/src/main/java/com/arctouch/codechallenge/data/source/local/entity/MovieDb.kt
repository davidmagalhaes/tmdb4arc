package com.arctouch.codechallenge.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import java.util.*

@Entity
data class MovieDb(
        @PrimaryKey
        @ColumnInfo(name = "_movie_id")
        var id: Long,
        var title: String,
        var overview: String? = null,
        var genreIds: List<Long>,
        var posterPath: String? = null,
        var backdropPath: String? = null,
        var releaseDate: LocalDate? = null
)