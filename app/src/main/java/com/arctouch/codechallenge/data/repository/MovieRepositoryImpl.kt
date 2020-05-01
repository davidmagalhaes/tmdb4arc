package com.arctouch.codechallenge.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.arctouch.codechallenge.data.boundary.local.MovieLocalDatasource
import com.arctouch.codechallenge.data.boundary.remote.MovieRemoteDatasource
import com.arctouch.codechallenge.data.scheduler.AppSchedulers
import com.arctouch.codechallenge.domain.error.PaginationExhaustedException
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.domain.repository.MovieRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepositoryImpl (
        private val appSchedulers: AppSchedulers,
        private val movieRemoteDatasource : MovieRemoteDatasource,
        private val movieLocalDatasource : MovieLocalDatasource
) : MovieRepository {

    override fun get(
            id : Long?,
            pageSize : Int,
            boundaryCallback: PagedList.BoundaryCallback<Movie>?
    ) : Flowable<PagedList<Movie>> {
        return RxPagedListBuilder(movieLocalDatasource.get(id), pageSize)
                .setFetchScheduler(appSchedulers.database())
                .setNotifyScheduler(appSchedulers.main())
                .setBoundaryCallback(boundaryCallback)
                .buildFlowable(BackpressureStrategy.BUFFER)
    }

    override fun fetchMovieDetails(id : Long) : Observable<Any> {
        return movieRemoteDatasource.fetchMovieDetails(id)
                .subscribeOn(appSchedulers.network())
                .flatMap { movie ->
                    movieLocalDatasource.upsert(listOf(movie))
                            .subscribeOn(appSchedulers.database())
                }
    }

    override fun fetchUpcomingMovies(page : Long) : Observable<Any> {
        return movieRemoteDatasource.fetchUpcomingMovies(page)
                .subscribeOn(appSchedulers.network())
                .flatMap { movies ->
                    if(page == 1L){
                        movieLocalDatasource.deleteAll()
                                .subscribeOn(appSchedulers.database())
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
                                .subscribeOn(appSchedulers.database())
                    }
                    else{
                        throw PaginationExhaustedException()
                    }
                }
    }

    override fun searchMovies(query : String, page : Long) : Observable<Any> {
        return movieRemoteDatasource.searchMovies(query, page)
                .subscribeOn(appSchedulers.network())
                .flatMap { movies ->
                    if(page == 1L){
                        movieLocalDatasource.deleteAll()
                                .subscribeOn(appSchedulers.database())
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
                                .subscribeOn(appSchedulers.database())
                    }
                    else{
                        throw PaginationExhaustedException()
                    }
                }
    }

}