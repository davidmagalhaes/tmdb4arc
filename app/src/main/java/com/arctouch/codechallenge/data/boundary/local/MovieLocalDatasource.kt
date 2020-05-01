package com.arctouch.codechallenge.data.boundary.local

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Flowable
import io.reactivex.Observable

interface MovieLocalDatasource {
    fun get(id : Long? = null) : DataSource.Factory<Int, Movie>
    fun upsert(movies : List<Movie>) : Observable<Any>
    fun deleteAll() : Observable<Any>
}