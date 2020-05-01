package com.arctouch.codechallenge.domain.model

import org.threeten.bp.LocalDate

data class Movie(
        val id: Long,
        val title: String,
        val overview: String? = null,
        var genres: List<Genre> = listOf(),
        val posterPath: String? = null,
        val backdropPath: String? = null,
        val releaseDate: LocalDate? = null
) : Comparable<Movie> {
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
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        return result
    }
}