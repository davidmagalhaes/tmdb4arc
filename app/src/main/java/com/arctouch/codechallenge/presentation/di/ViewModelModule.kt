package com.arctouch.codechallenge.presentation.di

import com.arctouch.codechallenge.domain.usecase.FetchUpcomingMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.GetMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.SearchMoviesUseCase
import com.arctouch.codechallenge.presentation.detail.MovieDetailsViewModel
import com.arctouch.codechallenge.presentation.home.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideHomeViewModel(
        getMoviesUseCase: GetMoviesUseCase,
        fetchUpcomingMoviesUseCase: FetchUpcomingMoviesUseCase,
        searchMoviesUseCase: SearchMoviesUseCase
    ) : HomeViewModel {
        return HomeViewModel(
                getMoviesUseCase,
                fetchUpcomingMoviesUseCase,
                searchMoviesUseCase
        )
    }

    @Provides
    fun provideMovieDetailsViewModel(
        getMoviesUseCase: GetMoviesUseCase
    ) : MovieDetailsViewModel {
        return MovieDetailsViewModel(
                getMoviesUseCase
        )
    }
}