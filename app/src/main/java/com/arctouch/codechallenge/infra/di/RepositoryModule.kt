package com.arctouch.codechallenge.infra.di

import com.arctouch.codechallenge.data.boundary.local.GenreLocalDatasource
import com.arctouch.codechallenge.data.boundary.local.MovieLocalDatasource
import com.arctouch.codechallenge.data.boundary.remote.GenreRemoteDatasource
import com.arctouch.codechallenge.data.boundary.remote.MovieRemoteDatasource
import com.arctouch.codechallenge.data.repository.GenreRepositoryImpl
import com.arctouch.codechallenge.data.repository.MovieRepositoryImpl
import com.arctouch.codechallenge.data.scheduler.AppSchedulers
import com.arctouch.codechallenge.domain.repository.GenreRepository
import com.arctouch.codechallenge.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        appSchedulers: AppSchedulers,
        movieLocalDatasource: MovieLocalDatasource,
        movieRemoteDatasource: MovieRemoteDatasource
    ) : MovieRepository {
        return MovieRepositoryImpl(
                appSchedulers,
                movieRemoteDatasource,
                movieLocalDatasource
        )
    }

    @Provides
    @Singleton
    fun provideGenreRepository(
          genreLocalDatasource: GenreLocalDatasource,
          genreRemoteDatasource: GenreRemoteDatasource
    ) : GenreRepository {
        return GenreRepositoryImpl(
                genreRemoteDatasource,
                genreLocalDatasource
        )
    }
}