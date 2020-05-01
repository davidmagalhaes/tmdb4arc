package com.arctouch.codechallenge.data.source.remote.impl

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.data.boundary.remote.MovieRemoteDatasource
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.data.source.remote.mapper.MovieRemoteMapper
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Observable

class MovieRemoteDatasourceImpl(
    private val tmdbApi: TmdbApi
) : MovieRemoteDatasource {
    override fun fetchMovieDetails(id : Long) : Observable<Movie> {
        return tmdbApi.movie(
                id,
                BuildConfig.TMDB_API_KEY
        )
    }

    override fun fetchUpcomingMovies(page : Long) : Observable<List<Movie>> {
        return tmdbApi.upcomingMovies(
                BuildConfig.TMDB_API_KEY,
                page,
                "primary_release_date.desc"
        ).map {
            MovieRemoteMapper.toEntity(it.results)
        }
    }

    override fun searchMovies(query : String, page : Long) : Observable<List<Movie>> {
        return tmdbApi.search(
                BuildConfig.TMDB_API_KEY,
                query,
                 page
        ).map {
            MovieRemoteMapper.toEntity(it.results)
        }
    }
}