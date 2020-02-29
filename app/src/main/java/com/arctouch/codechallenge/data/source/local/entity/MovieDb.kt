package com.arctouch.codechallenge.data.source.local.entity


import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class MovieDb(
        @PrimaryKey
        var id: Long,
        var title: String,
        var overview: String? = null,
        var genres: RealmList<GenreDb> = RealmList(),
        var genreIds: RealmList<Int> = RealmList(),
        var posterPath: String? = null,
        var backdropPath: String? = null,
        var releaseDate: Date? = null
) : RealmObject() {
    constructor() : this(0, "")
}