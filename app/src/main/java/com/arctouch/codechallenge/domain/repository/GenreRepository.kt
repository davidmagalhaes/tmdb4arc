package com.arctouch.codechallenge.domain.repository

import io.reactivex.Observable

interface GenreRepository {
    fun fetchGenres() : Observable<Any>
    fun count() : Observable<Long>
}