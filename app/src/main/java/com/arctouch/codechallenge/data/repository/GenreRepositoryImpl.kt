package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.boundary.local.GenreLocalDatasource
import com.arctouch.codechallenge.data.boundary.remote.GenreRemoteDatasource
import com.arctouch.codechallenge.data.source.local.impl.GenreLocalDatasourceImpl
import com.arctouch.codechallenge.data.source.remote.impl.GenreRemoteDatasourceImpl
import com.arctouch.codechallenge.domain.repository.GenreRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GenreRepositoryImpl(
        val genreRemoteDataSource : GenreRemoteDatasource,
        val genreLocalDatasource : GenreLocalDatasource
) : GenreRepository {

    override fun fetchGenres() : Observable<Any> {
        return genreRemoteDataSource.fetch()
                .subscribeOn(Schedulers.io())
                .flatMap {
                    genreLocalDatasource.upsert(it)
                        .subscribeOn(Schedulers.single())
                }
    }

    override fun count() : Observable<Long> {
        return genreLocalDatasource.count()
                .subscribeOn(Schedulers.single())
    }
}