package com.arctouch.codechallenge.presentation.di

import com.arctouch.codechallenge.infra.di.ApplicationComponent
import com.arctouch.codechallenge.presentation.detail.MovieDetailsActivity
import com.arctouch.codechallenge.presentation.home.HomeActivity
import dagger.Component

@PresentationScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [
            ViewModelModule::class
        ]
)
interface PresentationComponent {
    fun inject(homeActivity: HomeActivity)
    fun inject(detailsActivity: MovieDetailsActivity)
}