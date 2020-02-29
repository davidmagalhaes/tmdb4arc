package com.arctouch.codechallenge.data.source.local.impl

import com.arctouch.codechallenge.data.source.local.entity.GenreDb
import com.arctouch.codechallenge.data.source.local.mapper.GenreLocalMapper
import com.arctouch.codechallenge.domain.model.Genre
import io.reactivex.Observable
import io.realm.Realm

object GenreLocalDatasourceImpl {

    fun upsert(genres : List<Genre>) : Observable<Any> {
        return Observable.fromCallable {
            Realm.getDefaultInstance().use {realm ->
                realm.executeTransaction {
                    it.copyToRealmOrUpdate(
                            GenreLocalMapper.toDto(genres)
                    )
                }
            }
        }
    }

    fun count() : Observable<Long> {
        return Observable.fromCallable {
            Realm.getDefaultInstance().use {realm ->
                realm.where(GenreDb::class.java).count()
            }
        }
    }
}