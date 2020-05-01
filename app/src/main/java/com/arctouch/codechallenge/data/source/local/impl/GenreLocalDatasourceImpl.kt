package com.arctouch.codechallenge.data.source.local.impl

import com.arctouch.codechallenge.data.boundary.local.GenreLocalDatasource
import com.arctouch.codechallenge.data.source.local.dao.GenreDao
import com.arctouch.codechallenge.data.source.local.mapper.GenreLocalMapper
import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable

class GenreLocalDatasourceImpl(
        private val genreDao: GenreDao
) : GenreLocalDatasource {
    override fun upsert(genres : List<Genre>) : Observable<Any> {
        return genreDao.upsert(
                *GenreLocalMapper.toDto(genres).toTypedArray()
        ).toObservable()
                .map {  }
    }

    override fun count() : Observable<Long> {
        return genreDao.count()
                .toObservable()
    }
}