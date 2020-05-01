package com.arctouch.codechallenge.data.source.remote.impl

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.data.boundary.remote.GenreRemoteDatasource
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.data.source.remote.mapper.GenreRemoteMapper
import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable

class GenreRemoteDatasourceImpl(
        private val tmdbApi : TmdbApi
) : GenreRemoteDatasource {

    override fun fetch() : Observable<List<Genre>> {
        return tmdbApi.genres(
                BuildConfig.TMDB_API_KEY
        ).map {
            GenreRemoteMapper.toEntity(it.genres)
        }
    }
}