package com.jorgetargz.europa.data.room.utils

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromBoolean(value: Boolean?): Int? {
        return value?.let { if (it) 1 else 0 }
    }

    @TypeConverter
    fun toBoolean(value: Int?): Boolean? {
        return value?.let { it == 1 }
    }
}