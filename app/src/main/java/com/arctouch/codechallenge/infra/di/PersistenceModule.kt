package com.arctouch.codechallenge.infra.di

import android.content.Context
import androidx.room.Room
import com.arctouch.codechallenge.data.source.local.LocalDatabase
import com.arctouch.codechallenge.data.source.local.dao.GenreDao
import com.arctouch.codechallenge.data.source.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {
    @Provides
    @Singleton
    fun provideDatabase(
            context : Context
    ) : LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, "app")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(
            database: LocalDatabase
    ) : MovieDao {
        return database.getMovieDao()
    }

    @Provides
    @Singleton
    fun provideGenreDao(
            database: LocalDatabase
    ) : GenreDao {
        return database.getGenreDao()
    }
}