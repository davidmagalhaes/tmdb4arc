package com.arctouch.codechallenge.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GenreDb(
        @PrimaryKey
        @ColumnInfo(name = "_genre_id")
        var id: Long,
        var name: String
)
