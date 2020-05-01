package com.arctouch.codechallenge.infra.di

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.data.scheduler.AppSchedulers
import com.arctouch.codechallenge.data.scheduler.AppSchedulersImpl
import com.arctouch.codechallenge.data.source.remote.api.TmdbApi
import com.arctouch.codechallenge.infra.util.GsonLocalDateTypeAdapter
import com.arctouch.codechallenge.infra.util.Ipv4RoutingOverIpv6
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.*
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDate
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson() : Gson {
        val gsonBuilder = GsonBuilder()

        gsonBuilder.registerTypeAdapter(LocalDate::class.java, GsonLocalDateTypeAdapter)

        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
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

        return okHttp3ClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideAppSchedulers() : AppSchedulers {
        return AppSchedulersImpl
    }

    @Singleton
    @Provides
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gson : Gson
    ) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_WS_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Singleton
    @Provides
    fun provideTmdbApi(
            retrofit: Retrofit
    ) : TmdbApi {
        return retrofit.create(TmdbApi::class.java)
    }
}