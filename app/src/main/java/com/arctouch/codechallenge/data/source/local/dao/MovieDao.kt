package com.arctouch.codechallenge.data.source.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.arctouch.codechallenge.data.source.local.entity.MovieDb
import com.arctouch.codechallenge.data.source.local.entity.MovieFullJoin
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface MovieDao : BaseDao<MovieDb> {
    @Query("SELECT m.*, mg.* FROM MovieDb m LEFT JOIN MovieGenreDb mg ON (m._movie_id = mg.movie_id) LEFT JOIN GenreDb g ON (g._genre_id = mg.genre_id)")
    fun get() : DataSource.Factory<Int, MovieFullJoin>

    @Query("SELECT m.*, mg.* FROM MovieDb m LEFT JOIN MovieGenreDb mg ON (m._movie_id = mg.movie_id) LEFT JOIN GenreDb g ON (g._genre_id = mg.genre_id) WHERE _movie_id = :id")
    fun get(id : Long) : DataSource.Factory<Int, MovieFullJoin>

    @Query("DELETE FROM MovieDb")
    fun deleteAll() : Maybe<Int>
}