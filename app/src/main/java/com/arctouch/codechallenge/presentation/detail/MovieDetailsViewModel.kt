package com.arctouch.codechallenge.presentation.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.domain.usecase.GetMoviesUseCase
import com.arctouch.codechallenge.presentation.common.Result
import com.arctouch.codechallenge.presentation.common.ResultWrapper

class MovieDetailsViewModel(
        private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    var initialized = false

    var movie = MediatorLiveData<Result<Movie>>()

    fun init(id : Long){
        if(!initialized){
            initialized = true

            ResultWrapper.wrapFirst(
                    getMoviesUseCase.execute(id).map {
                        it.toList()
                    },
                    movie
            )
        }
    }
}