package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.data.repository.GenreRepository
import com.arctouch.codechallenge.data.repository.MovieRepository
import io.reactivex.Observable

object SearchMoviesUseCase {
    fun execute(query : String, page : Long = 1) : Observable<Any> {
        return GenreRepository.count().flatMap {
            if(it > 0){
                MovieRepository.searchMovies(query, page)
            }
            else {
                GenreRepository.fetchGenres()
                        .flatMap {
                            MovieRepository.searchMovies(query, page)
                        }
            }
        }
    }
}