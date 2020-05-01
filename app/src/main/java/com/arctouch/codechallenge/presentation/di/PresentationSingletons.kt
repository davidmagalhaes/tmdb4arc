package com.arctouch.codechallenge.presentation.di

import com.arctouch.codechallenge.infra.App

object PresentationSingletons {
    val daggerComponent by lazy {
        DaggerPresentationComponent.builder()
                .applicationComponent(App.applicationComponent)
                .build()
    }
}