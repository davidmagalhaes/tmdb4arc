package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.domain.repository.GenreRepository
import com.arctouch.codechallenge.domain.repository.MovieRepository
import io.reactivex.Observable

class FetchUpcomingMoviesUseCase(
        private val genreRepository: GenreRepository,
        private val movieRepository: MovieRepository
) {
    fun execute(page : Long = 1) : Observable<Any> {
        return genreRepository.count().flatMap {
            if(it > 0){
                movieRepository.fetchUpcomingMovies(page)
            }
            else {
                genreRepository.fetchGenres()
                        .flatMap {
                            movieRepository.fetchUpcomingMovies(page)
                        }
            }
        }
    }
}