package com.omar.route_todo_application.database

import androidx.room.TypeConverter
import java.util.Date


class DateConverter {

    @TypeConverter
    fun fromData(date: Date):Long
    {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }
}