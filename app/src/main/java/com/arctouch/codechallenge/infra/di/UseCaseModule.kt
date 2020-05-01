package com.arctouch.codechallenge.infra.di

import com.arctouch.codechallenge.domain.repository.GenreRepository
import com.arctouch.codechallenge.domain.repository.MovieRepository
import com.arctouch.codechallenge.domain.usecase.FetchGenresUseCase
import com.arctouch.codechallenge.domain.usecase.FetchUpcomingMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.GetMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideFetchUpcomingMoviesUseCase(
            genreRepository: GenreRepository,
            movieRepository: MovieRepository
    ) : FetchUpcomingMoviesUseCase {
        return FetchUpcomingMoviesUseCase(
                genreRepository,
                movieRepository
        )
    }

    @Provides
    @Singleton
    fun provideFetchGenresUseCase(
            genreRepository: GenreRepository
    ) : FetchGenresUseCase {
        return FetchGenresUseCase(
                genreRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetMoviesUseCase(
            movieRepository: MovieRepository
    ) : GetMoviesUseCase {
        return GetMoviesUseCase(
                movieRepository
        )
    }

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(
            genreRepository: GenreRepository,
            movieRepository: MovieRepository
    ) : SearchMoviesUseCase {
        return SearchMoviesUseCase(
                genreRepository,
                movieRepository
        )
    }
}