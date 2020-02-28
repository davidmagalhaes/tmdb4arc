package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.data.repository.MovieRepository
import io.reactivex.Observable

object FetchUpcomingMoviesUseCase {
    fun execute(page : Long = 1) : Observable<Any> {
        return MovieRepository.fetchUpcomingMovies(page)
    }
}