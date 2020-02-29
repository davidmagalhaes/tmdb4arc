package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.data.repository.MovieRepository
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Flowable

object GetMoviesUseCase {
    fun execute(id : Long? = null) : Flowable<List<Movie>> {
        return MovieRepository.get(id)
    }
}