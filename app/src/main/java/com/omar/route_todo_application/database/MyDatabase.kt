package com.omar.route_todo_application.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.omar.route_todo_application.models.Dao
import com.omar.route_todo_application.models.Todo

@Database(entities =  [Todo::class], version = 1)
abstract class MyDatabase: RoomDatabase() {

    abstract  fun todoDao(): Dao

    companion object{
        val DATABASE_NAME = "todo_database"
        var myDatabase: MyDatabase? = null

        fun getInstance(context: Application): MyDatabase {
            if(myDatabase==null) {
                Room.databaseBuilder(
                context, MyDatabase::class.java, DATABASE_NAME
                ).fallbackToDestructiveMigration()
                 .allowMainThreadQueries()
                 .build()
        }
            return myDatabase!!
        }
    }
}