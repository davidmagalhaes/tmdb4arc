package com.arctouch.codechallenge.presentation.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> AppCompatActivity.bindViewModel(crossinline factory: () -> T): T =
        T::class.java.let { `class` ->
            ViewModelProvider(this, object: ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    if (modelClass == `class`) {
                        @Suppress("UNCHECKED_CAST")
                        return factory() as T
                    }
                    throw IllegalArgumentException("Unexpected argument: $modelClass")
                }
            }).get(`class`)
        }