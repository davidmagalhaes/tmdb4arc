package com.arctouch.codechallenge.infra.di

import com.arctouch.codechallenge.data.boundary.local.GenreLocalDatasource
import com.arctouch.codechallenge.data.boundary.local.MovieLocalDatasource
import com.arctouch.codechallenge.data.boundary.remote.GenreRemoteDatasource
import com.arctouch.codechallenge.data.boundary.remote.MovieRemoteDatasource
import com.arctouch.codechallenge.data.source.local.dao.GenreDao
import com.arctouch.codechallenge.data.source.local.dao.MovieDao
import com.arctouch.codechallenge.data.source.local.impl.GenreLocalDatasourceImpl
import com.arctouch.codechallenge.data.source.local.impl.MovieLocalDatasourceImpl
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.data.source.remote.impl.GenreRemoteDatasourceImpl
import com.arctouch.codechallenge.data.source.remote.impl.MovieRemoteDatasourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatasourceModule {

    @Singleton
    @Provides
    fun provideMovieLocalDatasource(
            movideDao: MovieDao
    ) : MovieLocalDatasource {
        return MovieLocalDatasourceImpl(
                movideDao
        )
    }

    @Singleton
    @Provides
    fun provideMovieRemoteDatasource(
            tmdbApi: TmdbApi
    ) : MovieRemoteDatasource {
        return MovieRemoteDatasourceImpl(
                tmdbApi
        )
    }

    @Singleton
    @Provides
    fun provideGenreLocalDatasource(
            genreDao: GenreDao
    ) : GenreLocalDatasource {
        return GenreLocalDatasourceImpl(
                genreDao
        )
    }

    @Singleton
    @Provides
    fun provideGenreRemoteDatasource(
            tmdbApi: TmdbApi
    ) : GenreRemoteDatasource {
        return GenreRemoteDatasourceImpl(
                tmdbApi
        )
    }
}