package com.arctouch.codechallenge.presentation.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.domain.usecase.FetchGenresUseCase
import com.arctouch.codechallenge.domain.usecase.FetchUpcomingMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.GetMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.SearchMoviesUseCase
import com.arctouch.codechallenge.presentation.common.Result
import com.arctouch.codechallenge.presentation.common.ResultWrapper

class HomeViewModel : ViewModel() {

    var initialized = false
    var page = 1L

    val movies = MediatorLiveData<Result<List<Movie>>>()

    var loadingNextPage = false

    var searchFor : String? = null

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
        page = 1L
        searchFor = null

        ResultWrapper.wrapLoading(
                FetchUpcomingMoviesUseCase.execute(1)
                        .firstElement(),
                movies
        )
    }

    fun seachMovies(query : String){
        page = 1L
        searchFor = query

        ResultWrapper.wrapLoading(
            SearchMoviesUseCase.execute(query, 1)
                    .firstElement(),
            movies
        )
    }

    fun nextPage() {
        if(!loadingNextPage){
            loadingNextPage = true

            val observable = if(searchFor != null){
                SearchMoviesUseCase.execute(searchFor!!, page + 1)
            }
            else {
                FetchUpcomingMoviesUseCase.execute( page + 1)
            }

            ResultWrapper.wrapLoading(
                    observable
                            .firstElement()
                            .doOnSuccess {
                                page++
                            }
                            .doFinally {
                                loadingNextPage = false
                            },
                    movies
            )
        }
    }
}