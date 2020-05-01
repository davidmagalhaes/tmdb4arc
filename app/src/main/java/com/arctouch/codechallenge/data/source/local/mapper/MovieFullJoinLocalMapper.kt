package com.arctouch.codechallenge.data.source.local.mapper

import com.arctouch.codechallenge.data.common.EntityMapper
import com.arctouch.codechallenge.data.source.local.entity.MovieFullJoin
import com.arctouch.codechallenge.domain.model.Movie

object MovieFullJoinLocalMapper : EntityMapper<Movie, MovieFullJoin> {
    override val toDtoMapper: (Movie) -> MovieFullJoin
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val toEntityMapper: (MovieFullJoin) -> Movie = {
        val movie = MovieLocalMapper.toEntity(it.movie)

        movie.genres = GenreLocalMapper.toEntity(it.genres)

        movie
    }
}