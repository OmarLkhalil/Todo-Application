package com.omar.route_todo_application.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.omar.route_todo_application.R
import com.omar.route_todo_application.database.MyDatabase
import com.omar.route_todo_application.databinding.ActivityUpdateTodoBinding
import com.omar.route_todo_application.models.Todo
import java.util.*

class UpdateTodo: AppCompatActivity() {

    private lateinit var binding : ActivityUpdateTodoBinding
    private lateinit var todo    : Todo

    private val currentDate: Calendar = Calendar.getInstance()

    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
      binding = DataBindingUtil.setContentView(this, R.layout.activity_update_todo)
        todo = (intent.getSerializableExtra("Todo") as Todo)

        Log.i("Ut", "Update Todo Started")
        showData(todo)

        binding.submit.setOnClickListener{
            updateTodo(todo)
        }
    }

    private fun updateTodo(todo: Todo){
        val _todo : Todo  ?= null
        if(validate()){
            _todo?.name    = binding.titleContainer.editText?.text.toString()
            _todo?.details = binding.descContainer.editText?.text.toString()
            _todo?.date    = currentDate.time
            _todo?.isDone  = false
        }

        MyDatabase.getInstance(this)
            .todoDao()
            .updateTodo(todo)

    }

    private fun validate(): Boolean{

        var isValid = true

        if(binding.titleContainer.editText?.text.toString().isBlank()){
            binding.titleContainer.error = "Please enter a title"
            isValid = false
        }
        else {
            binding.titleContainer.error = null
        }

        if(binding.descContainer.editText?.text.toString().isBlank()){
            binding.descContainer.error = "Please enter a details"
            isValid = false
        }
        else
        {
            binding.descContainer.error = null
        }

        if(binding.date.text.isBlank()){
            binding.dateContainer.error = "Please enter a date"
            isValid = false
        }
        else
        {
            binding.dateContainer.error = null
        }

        return isValid
    }

    private fun showData(todo: Todo) {
        binding.titleContainer.editText?.setText(todo.name)
        binding.descContainer.editText?.setText(todo.details)
        binding.date.text = todo.date.toString()
    }
}