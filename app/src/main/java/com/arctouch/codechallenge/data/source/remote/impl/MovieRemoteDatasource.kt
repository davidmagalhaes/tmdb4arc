package com.arctouch.codechallenge.data.source.remote.impl

import com.arctouch.codechallenge.data.source.remote.api.ApiSingletons
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Observable
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

object MovieRemoteDatasource {
    fun fetchMovieDetails(id : Long) : Observable<Movie> {
        return ApiSingletons.tmdbApi.movie(
                id,
                TmdbApi.API_KEY,
                TmdbApi.DEFAULT_LANGUAGE
        )
    }

    fun fetchUpcomingMovies(page : Long = 1) : Observable<List<Movie>> {
        return ApiSingletons.tmdbApi.upcomingMovies(
                TmdbApi.API_KEY,
                TmdbApi.DEFAULT_LANGUAGE,
                page,
                TmdbApi.DEFAULT_REGION
        ).map {
            it.results
        }
    }
}