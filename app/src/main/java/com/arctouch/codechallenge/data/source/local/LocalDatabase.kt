package com.arctouch.codechallenge.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arctouch.codechallenge.data.source.local.dao.GenreDao
import com.arctouch.codechallenge.data.source.local.dao.MovieDao
import com.arctouch.codechallenge.data.source.local.entity.GenreDb
import com.arctouch.codechallenge.data.source.local.entity.MovieDb
import com.arctouch.codechallenge.data.source.local.entity.MovieGenreDb
import com.arctouch.codechallenge.infra.util.LocalDateTypeConverter
import com.arctouch.codechallenge.infra.util.SimpleListTypeConverter

@Database(
        entities = [
                MovieDb::class,
                GenreDb::class,
                MovieGenreDb::class
        ],
        version = 2,
        exportSchema = false
)
@TypeConverters(SimpleListTypeConverter::class, LocalDateTypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {
        abstract fun getMovieDao() : MovieDao
        abstract fun getGenreDao() : GenreDao
}