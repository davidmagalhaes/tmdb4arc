package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.data.repository.MovieRepository
import io.reactivex.Observable

object SearchMoviesUseCase {
    fun execute(query : String, page : Long = 1) : Observable<Any> {
        return MovieRepository.searchMovies(query, page)
    }
}