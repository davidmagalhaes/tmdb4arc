package com.arctouch.codechallenge.data.source.remote.impl

import com.arctouch.codechallenge.data.source.remote.api.ApiSingletons
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable

object GenreRemoteDatasource {

    fun fetch() : Observable<List<Genre>> {
        return ApiSingletons.tmdbApi.genres(
                TmdbApi.API_KEY,
                TmdbApi.DEFAULT_LANGUAGE
        ).map {
            it.genres
        }
    }
}