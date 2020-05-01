package com.arctouch.codechallenge.domain.usecase

import androidx.paging.PagedList
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.domain.repository.MovieRepository
import io.reactivex.Flowable

class GetMoviesUseCase(
        private val movieRepository: MovieRepository
) {
    fun execute(
            id : Long? = null,
            boundaryCallback: PagedList.BoundaryCallback<Movie>? = null
    ) : Flowable<PagedList<Movie>> {
        return movieRepository.get(id, 20, boundaryCallback)
    }
}