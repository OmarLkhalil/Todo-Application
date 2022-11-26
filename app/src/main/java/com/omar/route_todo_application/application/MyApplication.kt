package com.omar.route_todo_application.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omar.route_todo_application.R

class MyApplication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}