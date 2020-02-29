package com.arctouch.codechallenge.data.source.local.mapper

import com.arctouch.codechallenge.data.common.EntityMapper
import com.arctouch.codechallenge.data.source.local.entity.GenreDb
import com.arctouch.codechallenge.data.source.local.entity.MovieDb
import com.arctouch.codechallenge.domain.model.Movie
import io.realm.RealmList

object MovieLocalMapper : EntityMapper<Movie, MovieDb> {
    override val toDtoMapper: (Movie) -> MovieDb = {
        with(it){
            MovieDb(
                    id = id,
                    genreIds = RealmList<Int>().apply { addAll(genreIds) },
                    backdropPath = backdropPath,
                    genres = RealmList<GenreDb>().apply { addAll(GenreLocalMapper.toDto(genres)) },
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
                    genreIds = genreIds.toList(),
                    backdropPath = backdropPath,
                    genres = GenreLocalMapper.toEntity(genres),
                    overview = overview,
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    title = title
            )
        }
    }

}