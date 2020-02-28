package com.arctouch.codechallenge.infra

import android.app.Application
import android.util.Log
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.plugins.RxJavaPlugins
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        val TAG = App::class.java.simpleName

        val api by lazy {
            Retrofit.Builder()
                    .baseUrl(TmdbApi.URL)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(TmdbApi::class.java)
        }

        val realm by lazy {
            Realm.init(instance)
            Realm.setDefaultConfiguration(RealmConfiguration.Builder().
                    deleteRealmIfMigrationNeeded().build())

            Realm.getDefaultInstance()
        }

        lateinit var instance : App
    }

    init {
        instance = this

        RxJavaPlugins.setErrorHandler {
            Log.i(TAG, "RxJava error handled on Global Handler!")
            it.printStackTrace()
        }
    }
}