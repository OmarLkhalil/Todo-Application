package com.omar.route_todo_application.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int? = null,
    @ColumnInfo
    val name: String? = null,
    @ColumnInfo
    val details: String? = null,
    @ColumnInfo
    val date: Date? = null,
    @ColumnInfo
    val isDone: Boolean = false,
)