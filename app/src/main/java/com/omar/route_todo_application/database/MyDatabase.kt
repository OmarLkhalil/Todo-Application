package com.omar.route_todo_application.database

import android.app.Application
import android.content.Context
import androidx.room.*
import com.omar.route_todo_application.models.Dao
import com.omar.route_todo_application.models.Todo
import java.util.*

@TypeConverters(DateConverter::class)
@Database(entities =  [Todo::class], version = 1)
abstract class MyDatabase: RoomDatabase() {

    abstract  fun todoDao(): Dao

    companion object{
        val DATABASE_NAME = "todo_database"
        var myDatabase: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if(myDatabase==null) {
                Room.databaseBuilder(
                context, MyDatabase::class.java, DATABASE_NAME
                ).fallbackToDestructiveMigration()
                 .allowMainThreadQueries()
                 .build()
        }
            return myDatabase
        }
    }
}