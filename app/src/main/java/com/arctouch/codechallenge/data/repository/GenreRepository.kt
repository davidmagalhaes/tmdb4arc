package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.source.local.impl.GenreLocalDatasourceImpl
import com.arctouch.codechallenge.data.source.remote.impl.GenreRemoteDatasource
import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object GenreRepository {

    val genreRemoteDataSource = GenreRemoteDatasource
    val genreLocalDatasource = GenreLocalDatasourceImpl

    fun fetchGenres() : Observable<Any> {
        return genreRemoteDataSource.fetch()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    genreLocalDatasource.upsert(it)
                }
    }
}