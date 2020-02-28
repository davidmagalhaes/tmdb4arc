package com.arctouch.codechallenge.domain.usecase

import com.arctouch.codechallenge.data.repository.GenreRepository
import io.reactivex.Observable

object FetchGenresUseCase {
    fun execute() : Observable<Any> {
        return GenreRepository.fetchGenres()
    }
}