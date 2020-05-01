package com.arctouch.codechallenge.data.source.remote.api

import com.arctouch.codechallenge.data.source.remote.entity.GenreResponse
import com.arctouch.codechallenge.data.source.remote.entity.UpcomingMoviesResponse
import com.arctouch.codechallenge.domain.model.Movie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("genre/movie/list")
    fun genres(
        @Query("api_key") apiKey: String
    ): Observable<GenreResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Long,
        @Query("sort_by") sortBy : String
    ): Observable<UpcomingMoviesResponse>

    @GET("movie/{id}")
    fun movie(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String
    ): Observable<Movie>

    @GET("search/movie")
    fun search(
        @Query("api_key") apiKey: String,
        @Query("query") query : String,
        @Query("page") page: Long
    ): Observable<UpcomingMoviesResponse>
}
