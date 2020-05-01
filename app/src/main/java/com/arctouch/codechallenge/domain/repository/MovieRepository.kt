package com.arctouch.codechallenge.domain.repository

import androidx.paging.PagedList
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Flowable
import io.reactivex.Observable

interface MovieRepository {
    fun get(
            id : Long? = null,
            pageSize : Int = 20,
            boundaryCallback: PagedList.BoundaryCallback<Movie>? = null
    ) : Flowable<PagedList<Movie>>
    fun fetchMovieDetails(id : Long) : Observable<Any>
    fun fetchUpcomingMovies(page : Long = 1L) : Observable<Any>
    fun searchMovies(query : String, page : Long = 1) : Observable<Any>
}