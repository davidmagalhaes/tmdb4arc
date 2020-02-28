package com.arctouch.codechallenge.domain.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class GenreResponse(val genres: List<Genre>)

open class Genre(
        @PrimaryKey
        var id: Int,
        var name: String
) : RealmObject() {
        public constructor() : this(0, "")
}

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int
)

open class Movie(
        @PrimaryKey
        var id: Int,
        var title: String,
        var overview: String? = null,
        var genres: RealmList<Genre>? = null,
        @SerializedName("genre_ids") var genreIds: RealmList<Int>? = null,
        @SerializedName("poster_path") var posterPath: String? = null,
        @SerializedName("backdrop_path") var backdropPath: String? = null,
        @SerializedName("release_date") var releaseDate: String? = null
) : RealmObject() {
        public constructor() : this(0, "")
}
