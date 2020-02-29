package com.arctouch.codechallenge.domain.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import org.threeten.bp.LocalDate
import java.util.*

data class GenreResponse(val genres: List<Genre>)

class Genre(
        @SerializedName("id")
        var id: Long,
        var name: String
) {
        constructor() : this(0, "")
}

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<Movie>,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int
)

data class Movie(
        @SerializedName("id")
        var id: Long,
        var title: String,
        var overview: String? = null,
        var genres: List<Genre> = listOf(),
        @SerializedName("genre_ids") var genreIds: List<Int> = listOf(),
        @SerializedName("poster_path") var posterPath: String? = null,
        @SerializedName("backdrop_path") var backdropPath: String? = null,
        @SerializedName("release_date") var releaseDate: Date? = null
) : Comparable<Movie> {
        constructor() : this(0, "")

        override fun compareTo(other: Movie): Int {
            return (id - other.id).toInt()
        }

        override fun equals(other: Any?): Boolean {
            return  if(other is Movie){
                id == other.id
            }
            else {
                return false
            }
        }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + genres.hashCode()
        result = 31 * result + genreIds.hashCode()
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        return result
    }
}
