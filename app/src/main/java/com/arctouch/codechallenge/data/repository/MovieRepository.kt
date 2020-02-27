package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.source.remote.impl.MovieRemoteDatasource
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object MovieRepository {

    val movieRemoteDatasource = MovieRemoteDatasource

    fun fetchMovieDetails(id : Long) : Observable<Movie> {
        return movieRemoteDatasource.fetchMovieDetails(id)
                .observeOn(Schedulers.io())
    }

    fun fetchUpcomingMovies(page : Long = 1) : Observable<List<Movie>> {
        return movieRemoteDatasource.fetchUpcomingMovies(page)
                .subscribeOn(Schedulers.io())
    }

}