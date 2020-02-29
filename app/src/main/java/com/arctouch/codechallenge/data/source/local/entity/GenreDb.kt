package com.arctouch.codechallenge.data.source.local.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GenreDb(
        @PrimaryKey
        var id: Long,
        var name: String
) : RealmObject() {
        constructor() : this(0, "")
}
