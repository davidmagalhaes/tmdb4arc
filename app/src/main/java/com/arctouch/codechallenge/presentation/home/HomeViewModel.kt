package com.arctouch.codechallenge.presentation.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.domain.usecase.FetchGenresUseCase
import com.arctouch.codechallenge.domain.usecase.FetchUpcomingMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.GetMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.SearchMoviesUseCase
import com.arctouch.codechallenge.presentation.common.LiveDataWrapper
import com.arctouch.codechallenge.presentation.common.Result
import com.arctouch.codechallenge.presentation.common.ResultWrapper
import io.reactivex.Flowable
import io.reactivex.Maybe

class HomeViewModel(
        private val getMoviesUseCase: GetMoviesUseCase,
        private val fetchUpcomingMoviesUseCase: FetchUpcomingMoviesUseCase,
        private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    var initialized = false
    var page = 0L

    var searchFor : String? = null

    val movies = MediatorLiveData<PagedList<Movie>>()
    val loadingNextPage = MutableLiveData<Boolean>(false)

    fun init(){
        if(!initialized){
            initialized = true

            val liveData = LiveDataReactiveStreams.fromPublisher(
                getMoviesUseCase.execute(null, object : PagedList.BoundaryCallback<Movie>() {
                    override fun onItemAtFrontLoaded(itemAtFront: Movie) {
                        page++
                    }
                    override fun onZeroItemsLoaded() {
                        LiveDataWrapper.wrapLoading(
                                fetchNextPage().firstElement(),
                                movies
                        )
                    }
                })
            )

            movies.addSource(liveData) {
                movies.postValue(it)
            }
        }
    }

    fun fetchUpcomingMovies() {
        page = 0L
        searchFor = null

        LiveDataWrapper.wrapLoading(
                fetchUpcomingMoviesUseCase.execute(1)
                        .firstElement(),
                movies
        )
    }

    fun seachMovies(query : String){
        page = 0L
        searchFor = query

        LiveDataWrapper.wrapLoading(
                searchMoviesUseCase.execute(query, 1)
                      .firstElement(),
                movies
        )
    }

    private fun fetchNextPage() : Flowable<Any> {
        if(loadingNextPage.value == false){
            loadingNextPage.postValue(true)

            val observable = if(searchFor != null){
                searchMoviesUseCase.execute(searchFor!!, page + 1)
            }
            else {
                fetchUpcomingMoviesUseCase.execute( page + 1)
            }

            return observable.firstElement()
                    .doFinally {
                        loadingNextPage.postValue(false)
                    }
                    .toFlowable()
        }
        else {
            return Flowable.just(Any())
        }
    }
}