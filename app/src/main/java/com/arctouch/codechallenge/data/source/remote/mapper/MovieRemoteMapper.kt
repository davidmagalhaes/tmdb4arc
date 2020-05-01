package com.arctouch.codechallenge.data.source.remote.mapper

import com.arctouch.codechallenge.data.common.EntityMapper
import com.arctouch.codechallenge.data.source.remote.entity.MovieObject
import com.arctouch.codechallenge.domain.model.Movie

object MovieRemoteMapper : EntityMapper<Movie, MovieObject> {
    override val toDtoMapper: (Movie) -> MovieObject
        get() = TODO("not implemented")

    override val toEntityMapper: (MovieObject) -> Movie = {
        Movie(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                overview = it.overview,
                genres = GenreRemoteMapper.toEntity(it.genres ?: emptyList()),
                backdropPath = it.backdropPath
        )
    }
}