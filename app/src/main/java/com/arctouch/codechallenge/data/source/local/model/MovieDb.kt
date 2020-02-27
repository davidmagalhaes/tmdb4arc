package com.arctouch.codechallenge.data.source.local.model


data class MovieDb (
        val id: Int,
        val title: String,
        val overview: String?,
        val genres_ids: List<Int>?
)