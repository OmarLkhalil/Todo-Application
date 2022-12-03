package com.omar.route_todo_application.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.io.Serializable

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int? = null,
    @ColumnInfo
    var name: String? = null,
    @ColumnInfo
    var details: String? = null,
    @ColumnInfo
    var date: Date? = null,
    @ColumnInfo
    var isDone: Boolean = false,
):Serializable