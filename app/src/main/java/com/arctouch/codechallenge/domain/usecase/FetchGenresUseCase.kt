package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.domain.repository.GenreRepository
import io.reactivex.Observable

class FetchGenresUseCase(
        private val genreRepository: GenreRepository
) {
    fun execute() : Observable<Any> {
        return genreRepository.fetchGenres()
    }
}