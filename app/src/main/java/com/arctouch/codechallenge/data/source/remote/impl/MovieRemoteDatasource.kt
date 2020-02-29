package com.arctouch.codechallenge.data.source.remote.impl

import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.infra.App.Companion.api
import io.reactivex.Observable

object MovieRemoteDatasource {
    fun fetchMovieDetails(id : Long) : Observable<Movie> {
        return api.movie(
                id,
                TmdbApi.API_KEY,
                TmdbApi.DEFAULT_LANGUAGE
        )
    }

    fun fetchUpcomingMovies(page : Long = 1) : Observable<List<Movie>> {
        return api.upcomingMovies(
                TmdbApi.API_KEY,
                page,
//                TmdbApi.DEFAULT_LANGUAGE,
//                TmdbApi.DEFAULT_REGION,
                "primary_release_date.desc"
        ).map {
            it.results
        }
    }

    fun searchMovies(query : String, page : Long = 1) : Observable<List<Movie>> {
        return api.search(
                TmdbApi.API_KEY,
//                TmdbApi.DEFAULT_LANGUAGE,
//                TmdbApi.DEFAULT_REGION,
                query,
                 page
        ).map {
            it.results
        }
    }
}