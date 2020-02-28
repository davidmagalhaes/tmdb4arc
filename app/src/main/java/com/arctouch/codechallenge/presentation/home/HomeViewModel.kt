package com.arctouch.codechallenge.presentation.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import br.com.softbuilder.appplus2.presentation.common.Result
import br.com.softbuilder.appplus2.presentation.common.ResultWrapper
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.domain.usecase.FetchGenresUseCase
import com.arctouch.codechallenge.domain.usecase.FetchUpcomingMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.GetMoviesUseCase

class HomeViewModel : ViewModel() {

    var initialized = false

    val movies = MediatorLiveData<Result<List<Movie>>>()

    fun init(){
        if(!initialized){
            initialized = true

            ResultWrapper.wrap(
                    GetMoviesUseCase.execute(),
                    movies
            )
        }
    }

    fun fetchUpcomingMovies() {
        ResultWrapper.wrapLoading(
                FetchGenresUseCase.execute()
                    .flatMap {
                        FetchUpcomingMoviesUseCase.execute()
                    }
                    .firstElement(),
                movies
        )
    }
}