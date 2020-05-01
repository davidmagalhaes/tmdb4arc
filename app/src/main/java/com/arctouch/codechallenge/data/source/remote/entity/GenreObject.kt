package com.arctouch.codechallenge.data.source.remote.entity

import com.google.gson.annotations.SerializedName

class GenreObject(
        @SerializedName("id")
        var id: Long,
        var name: String
) {
    constructor() : this(0, "")
}