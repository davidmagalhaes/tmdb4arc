package com.arctouch.codechallenge.data.boundary.remote

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.data.source.remote.mapper.GenreRemoteMapper
import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable

interface GenreRemoteDatasource {
    fun fetch() : Observable<List<Genre>>
}