package com.arctouch.codechallenge.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.arctouch.codechallenge.data.source.local.entity.GenreDb
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface GenreDao : BaseDao<GenreDb> {

    @Query("SELECT count(*) FROM GenreDb")
    fun count() : Maybe<Long>

}