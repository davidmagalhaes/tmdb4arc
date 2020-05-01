package com.arctouch.codechallenge.infra

import android.app.Application
import android.util.Log
import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.domain.usecase.FetchGenresUseCase
import com.arctouch.codechallenge.infra.di.DaggerApplicationComponent
import com.facebook.stetho.Stetho
import io.reactivex.plugins.RxJavaPlugins



class App : Application() {
    companion object {
        val TAG = App::class.java.simpleName

        val applicationComponent by lazy {
            DaggerApplicationComponent.builder()
                    .context(instance)
                    .build()
        }

        lateinit var instance : App
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            Log.i(TAG, "RxJava error handled on Global Handler!")
            it.printStackTrace()
        }

        if(BuildConfig.DEBUG){
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
            )
        }

        applicationComponent.inject(this)
    }
}