package com.omar.route_todo_application.models

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@androidx.room.Dao
interface Dao {

    @Insert
    suspend fun addTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM Todo")
    suspend fun getAllTodos(query: Query): List<Todo>
}