package com.arctouch.codechallenge.data.source.local.mapper

import com.arctouch.codechallenge.data.common.EntityMapper
import com.arctouch.codechallenge.data.source.local.entity.GenreDb
import com.arctouch.codechallenge.domain.model.Genre

object GenreLocalMapper : EntityMapper<Genre, GenreDb> {
    override val toDtoMapper: (Genre) -> GenreDb = {
        with(it) {
            GenreDb(
                    id = id,
                    name = name
            )
        }
    }

    override val toEntityMapper: (GenreDb) -> Genre = {
        with(it) {
            Genre(
                    id = id,
                    name = name
            )
        }
    }
}