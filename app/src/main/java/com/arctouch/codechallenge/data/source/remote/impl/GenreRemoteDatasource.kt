package com.arctouch.codechallenge.data.source.remote.impl

import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.domain.model.Genre
import com.arctouch.codechallenge.infra.App.Companion.api
import io.reactivex.Observable

object GenreRemoteDatasource {

    fun fetch() : Observable<List<Genre>> {
        return api.genres(
                TmdbApi.API_KEY,
                TmdbApi.DEFAULT_LANGUAGE
        ).map {
            it.genres
        }
    }
}