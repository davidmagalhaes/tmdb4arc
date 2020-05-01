package com.arctouch.codechallenge.infra.util

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

object SimpleListTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toListLong(value: String?): List<Long> {
        val result = arrayListOf<Long>()

        value?.replace("[", "")
                ?.replace("]", "")
                ?.split(",")
                ?.forEach {
                    if(it.isNotEmpty()){
                        result.add(it.toLong())
                    }
                }

        return result
    }

    @TypeConverter
    @JvmStatic
    fun fromListLong(value: List<Long>?): String? {
        return value?.let { "[${it.joinToString(",")}]" }
    }
}