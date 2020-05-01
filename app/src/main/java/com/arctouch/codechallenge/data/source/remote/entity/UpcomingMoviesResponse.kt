package com.arctouch.codechallenge.data.source.remote.entity

import com.google.gson.annotations.SerializedName

data class UpcomingMoviesResponse(
        val page: Int,
        val results: List<MovieObject>,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int
)