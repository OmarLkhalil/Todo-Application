package com.omar.route_todo_application.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int,
    val name: String? = null,
    val details: String? = null,
    val date: Date? = null,
    val isDone: Boolean = false,
)