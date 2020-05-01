package com.arctouch.codechallenge.data.source.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.reactivex.Maybe

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg data : T) : Maybe<List<Long>>
}