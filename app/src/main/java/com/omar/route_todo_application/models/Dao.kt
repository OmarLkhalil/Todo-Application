package com.omar.route_todo_application.models

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@androidx.room.Dao
interface Dao {

    @Insert
    fun addTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("select * from Todo")
    fun getAllTodos(): List<Todo>
}