package com.omar.route_todo_application.models

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert
    fun addTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("select * from Todo")
    fun getAllTodos(): List<Todo>

    @Query("select * from Todo where date = :dateParam")
    fun getTodosByDate(dateParam: Date): List<Todo>

    @Query("select * from todo where id = :id")
    fun getTaskById(id:Int):Todo
}