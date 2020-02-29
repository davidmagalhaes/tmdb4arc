package com.arctouch.codechallenge.data.source.remote.api

import com.arctouch.codechallenge.domain.model.GenreResponse
import com.arctouch.codechallenge.domain.model.Movie
import com.arctouch.codechallenge.domain.model.UpcomingMoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    companion object {
        const val URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "1f54bd990f1cdfb230adb312546d765d"
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }


    @GET("genre/movie/list")
    fun genres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Long,
//        @Query("language") language: String,
//        @Query("region") region: String,
        @Query("sort_by") sortBy : String
    ): Observable<UpcomingMoviesResponse>

    @GET("movie/{id}")
    fun movie(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Movie>

    @GET("search/movie")
    fun search(
        @Query("api_key") apiKey: String,
        @Query("query") query : String,
        @Query("page") page: Long
    ): Observable<UpcomingMoviesResponse>
}
