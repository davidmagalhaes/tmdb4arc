package com.arctouch.codechallenge.infra

import android.app.Application
import android.util.Log
import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.data.source.local.entity.MovieDb
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.domain.usecase.FetchGenresUseCase
import com.arctouch.codechallenge.infra.util.Ipv4RoutingOverIpv6
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.*
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class App : Application() {

    companion object {
        val TAG = App::class.java.simpleName

        val gson by lazy {
            val gsonBuilder = GsonBuilder()

            gsonBuilder.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date?> {
                var df: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                @Throws(JsonParseException::class)
                override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
                    return try {
                        df.parse(json.asString)
                    } catch (e: ParseException) {
                        null
                    }
                }
            })

            gsonBuilder.create()
        }

        val okHttpClient by lazy {
            val okHttp3ClientBuilder = OkHttpClient.Builder()
                    .connectionPool(ConnectionPool(10, 1, TimeUnit.MINUTES))
                    .readTimeout(BuildConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(BuildConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(BuildConfig.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(false)
                    .dns(Ipv4RoutingOverIpv6())
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })


            if(BuildConfig.DEBUG){
                okHttp3ClientBuilder.addNetworkInterceptor(StethoInterceptor())
            }

            okHttp3ClientBuilder.build()
        }

        val api by lazy {
            Retrofit.Builder()
                    .baseUrl(TmdbApi.URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()
                    .create(TmdbApi::class.java)
        }

        val realm by lazy {
            Realm.getDefaultInstance()
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

        Realm.init(instance)
        Realm.setDefaultConfiguration(
                RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .schemaVersion(1)
                        .build()
        )

        //TODO resolver o problema de cache pra poder remover isso
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.delete(MovieDb::class.java)
            }
        }

        if(BuildConfig.DEBUG){
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(RealmInspectorModulesProvider.builder(instance).build())
                    .build()
            )
        }

        val d = FetchGenresUseCase.execute().subscribe {
            //Do nothing
        }
    }
}