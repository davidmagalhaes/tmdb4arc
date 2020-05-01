package com.arctouch.codechallenge.data.boundary.local

import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable

interface GenreLocalDatasource {
    fun upsert(genres : List<Genre>) : Observable<Any>
    fun count() : Observable<Long>
}