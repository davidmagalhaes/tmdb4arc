package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.source.local.impl.MovieLocalDatasourceImpl
import com.arctouch.codechallenge.data.source.remote.impl.MovieRemoteDatasource
import com.arctouch.codechallenge.domain.error.PaginationExhaustedException
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object MovieRepository {

    val movieRemoteDatasource = MovieRemoteDatasource
    val movieLocalDatasource = MovieLocalDatasourceImpl

    fun get(id : Long? = null) : Flowable<List<Movie>> {
        return movieLocalDatasource.get(id)
    }

    fun fetchMovieDetails(id : Long) : Observable<Any> {
        return movieRemoteDatasource.fetchMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .flatMap { movie ->
                    movieLocalDatasource.upsert(listOf(movie))
                }
    }

    fun fetchUpcomingMovies(page : Long = 1L) : Observable<Any> {
        return movieRemoteDatasource.fetchUpcomingMovies(page)
                .subscribeOn(Schedulers.io())
                .flatMap { movies ->
                    if(page == 1L){
                        movieLocalDatasource.deleteAll()
                                .map {
                                    movies
                                }
                    }
                    else {
                        Observable.just(movies)
                    }
                }
                .flatMap { movies ->
                    if(movies.isNotEmpty()) {
                        movieLocalDatasource.upsert(movies)
                    }
                    else{
                        throw PaginationExhaustedException()
                    }
                }
    }

    fun searchMovies(query : String, page : Long = 1) : Observable<Any> {
        return movieRemoteDatasource.searchMovies(query, page)
                .subscribeOn(Schedulers.io())
                .flatMap { movies ->
                    if(page == 1L){
                        movieLocalDatasource.deleteAll()
                                .map {
                                    movies
                                }
                    }
                    else {
                        Observable.just(movies)
                    }
                }
                .flatMap { movies ->
                    if(movies.isNotEmpty()) {
                        movieLocalDatasource.upsert(movies)
                    }
                    else{
                        throw PaginationExhaustedException()
                    }
                }
    }

}