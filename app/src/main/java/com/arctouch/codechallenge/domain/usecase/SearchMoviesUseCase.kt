package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.domain.repository.GenreRepository
import com.arctouch.codechallenge.domain.repository.MovieRepository
import io.reactivex.Observable

class SearchMoviesUseCase(
        private val genreRepository: GenreRepository,
        private val movieRepository: MovieRepository
) {
    fun execute(query : String, page : Long = 1) : Observable<Any> {
        return genreRepository.count().flatMap {
            if(it > 0){
                movieRepository.searchMovies(query, page)
            }
            else {
                genreRepository.fetchGenres()
                        .flatMap {
                            movieRepository.searchMovies(query, page)
                        }
            }
        }
    }
}