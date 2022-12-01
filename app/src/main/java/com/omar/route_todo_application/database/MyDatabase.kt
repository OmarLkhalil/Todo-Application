package com.omar.route_todo_application.database

import android.content.Context
import androidx.room.*
import com.omar.route_todo_application.models.Dao
import com.omar.route_todo_application.models.Todo

@TypeConverters(DateConverter::class)
@Database(entities =  [Todo::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract  fun todoDao(): Dao

        companion object{
            private var myDataBase:MyDatabase?=null
            fun getInstance(context:Context):MyDatabase{
                if(myDataBase==null){
                    // create object
                    myDataBase = Room.databaseBuilder(
                        context,MyDatabase::class.java,
                        "tasks-database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
                }
                return myDataBase!!
            }
        }
}