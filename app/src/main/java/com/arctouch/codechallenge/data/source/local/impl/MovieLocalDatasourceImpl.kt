package com.arctouch.codechallenge.data.source.local.impl

import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.infra.App.Companion.realm
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.Realm

object MovieLocalDatasourceImpl {
    fun get(id : Int? = null) : Flowable<List<Movie>> {
        val query = realm.where(Movie::class.java)

        id?.let {
            query.equalTo("id", it)
        }

        return query
                .findAllAsync()
                .asFlowable()
                .map {
                    ArrayList<Movie>().apply {
                        addAll(it.toList())
                    }
                }
    }

    fun upsert(movies : List<Movie>) : Observable<Any> {
        return Observable.fromCallable {
            Realm.getDefaultInstance().use {realm ->
                realm.executeTransaction {
                    it.copyToRealmOrUpdate(movies)
                }
            }
        }
    }

}