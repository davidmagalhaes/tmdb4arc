package com.arctouch.codechallenge.data.source.remote.mapper

import com.arctouch.codechallenge.data.common.EntityMapper
import com.arctouch.codechallenge.data.source.remote.entity.GenreObject
import com.arctouch.codechallenge.domain.model.Genre

object GenreRemoteMapper : EntityMapper<Genre, GenreObject> {
    override val toDtoMapper: (Genre) -> GenreObject
        get() = TODO("not implemented")

    override val toEntityMapper: (GenreObject) -> Genre = {
        Genre(
                id = it.id,
                name = it.name
        )
    }
}