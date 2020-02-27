package com.arctouch.codechallenge.data.repository

import com.arctouch.codechallenge.data.source.remote.impl.GenreRemoteDatasource
import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object GenreRepository {

    val genreRemoteDataSource = GenreRemoteDatasource

    fun fetchGenres() : Observable<List<Genre>> {
        return genreRemoteDataSource.fetch()
                .observeOn(Schedulers.io())
    }
}