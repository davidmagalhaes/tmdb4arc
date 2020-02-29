package com.arctouch.codechallenge.data.source.local.impl

import com.arctouch.codechallenge.data.source.local.entity.GenreDb
import com.arctouch.codechallenge.data.source.local.entity.MovieDb
import com.arctouch.codechallenge.data.source.local.mapper.MovieLocalMapper
import com.arctouch.codechallenge.domain.model.Genre
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.infra.App
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList
import io.realm.Sort

object MovieLocalDatasourceImpl {
    fun get(id : Long? = null) : Flowable<List<Movie>> {
        val realm = App.realm
        val query = realm.where(MovieDb::class.java)

        id?.let {
            query.equalTo("id", it)
        }

        return query
                .sort("releaseDate", Sort.DESCENDING)
                .findAllAsync()
                .asFlowable()
                .map {
                    MovieLocalMapper.toEntity(it.toList())
                }
    }

    fun upsert(movies : List<Movie>) : Observable<Any> {
        return Observable.fromCallable {
            Realm.getDefaultInstance().use {outerRealm ->
                outerRealm.executeTransaction { realm ->
                    val moviesRealm = realm.copyToRealmOrUpdate(
                            MovieLocalMapper.toDto(movies)
                    )

                    moviesRealm.forEach {
                        it.genres = RealmList<GenreDb>().apply {
                            addAll(
                                realm.where(GenreDb::class.java).`in`(
                                        "id", Array(it.genreIds.size){idx ->
                                    it.genreIds.get(idx)
                                }).findAll().toList()
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteAll() : Observable<Any> {
        return Observable.fromCallable {
            Realm.getDefaultInstance().use { outerRealm ->
                outerRealm.executeTransaction { realm ->
                    realm.where(MovieDb::class.java).findAll().deleteAllFromRealm()
                }
            }
        }
    }

}