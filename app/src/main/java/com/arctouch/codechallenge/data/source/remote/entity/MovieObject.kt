package com.arctouch.codechallenge.data.source.remote.entity

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

data class MovieObject(
        @SerializedName("id")
        var id: Long,
        var title: String,
        var overview: String? = null,
        var genres: List<GenreObject>? = listOf(),
        @SerializedName("genre_ids")
        var genreIds: List<Int> = listOf(),
        @SerializedName("poster_path")
        var posterPath: String? = null,
        @SerializedName("backdrop_path")
        var backdropPath: String? = null,
        @SerializedName("release_date")
        var releaseDate: LocalDate? = null
)