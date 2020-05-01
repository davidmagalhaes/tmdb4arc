package com.arctouch.codechallenge.data.source.local.impl

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.paging.toFlowable
import com.arctouch.codechallenge.data.boundary.local.MovieLocalDatasource
import com.arctouch.codechallenge.data.source.local.dao.MovieDao
import com.arctouch.codechallenge.data.source.local.entity.MovieDb
import com.arctouch.codechallenge.data.source.local.mapper.MovieFullJoinLocalMapper
import com.arctouch.codechallenge.data.source.local.mapper.MovieLocalMapper
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class MovieLocalDatasourceImpl(
        private val movieDao: MovieDao
) : MovieLocalDatasource {
    override fun get(id : Long?) : DataSource.Factory<Int, Movie> {
        return movieDao.get().mapByPage {
            MovieFullJoinLocalMapper.toEntity(it)
        }
    }

    override fun upsert(movies : List<Movie>) : Observable<Any> {
        return movieDao.upsert(
                *MovieLocalMapper.toDto(movies).toTypedArray()
        ).toObservable()
                .map {  }
    }

    override fun deleteAll() : Observable<Any> {
        return movieDao.deleteAll()
                .toObservable()
                .map {  }
    }
}