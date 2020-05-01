package com.arctouch.codechallenge.data.source.local.mapper

import com.arctouch.codechallenge.data.common.EntityMapper
import com.arctouch.codechallenge.data.source.local.entity.MovieDb
import com.arctouch.codechallenge.domain.model.Movie

object MovieLocalMapper : EntityMapper<Movie, MovieDb> {
    override val toDtoMapper: (Movie) -> MovieDb = {
        with(it){
            MovieDb(
                    id = id,
                    genreIds = it.genres.map { it.id },
                    backdropPath = backdropPath,
                    overview = overview,
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    title = title
            )
        }
    }

    override val toEntityMapper: (MovieDb) -> Movie = {
        with(it){
            Movie(
                    id = id,
                    backdropPath = backdropPath,
                    overview = overview,
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    title = title
            )
        }
    }

}