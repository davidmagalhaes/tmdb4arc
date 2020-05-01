package com.arctouch.codechallenge.infra.di

import android.content.Context
import com.arctouch.codechallenge.domain.usecase.FetchGenresUseCase
import com.arctouch.codechallenge.domain.usecase.FetchUpcomingMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.GetMoviesUseCase
import com.arctouch.codechallenge.domain.usecase.SearchMoviesUseCase
import com.arctouch.codechallenge.infra.App
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            PersistenceModule::class,
            NetworkModule::class,
            DatasourceModule::class,
            RepositoryModule::class,
            UseCaseModule::class
        ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context : Context) : Builder
        fun build(): ApplicationComponent
    }

    fun inject(app : App)

    fun fetchGenresUseCase() : FetchGenresUseCase
    fun fetchUpcomingMoviesUseCase() : FetchUpcomingMoviesUseCase
    fun getMoviesUseCase() : GetMoviesUseCase
    fun searchMoviesUseCase() : SearchMoviesUseCase
}