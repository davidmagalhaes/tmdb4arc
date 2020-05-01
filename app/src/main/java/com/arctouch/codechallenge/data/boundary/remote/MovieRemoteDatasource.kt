package com.arctouch.codechallenge.data.boundary.remote

import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Observable

interface MovieRemoteDatasource {
    fun fetchMovieDetails(id : Long) : Observable<Movie>
    fun fetchUpcomingMovies(page : Long = 1) : Observable<List<Movie>>
    fun searchMovies(query : String, page : Long = 1) : Observable<List<Movie>>
}