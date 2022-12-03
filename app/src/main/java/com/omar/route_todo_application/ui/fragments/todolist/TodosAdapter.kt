package com.omar.route_todo_application.ui.fragments.todolist

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omar.route_todo_application.databinding.ItemTodoBinding
import com.omar.route_todo_application.models.Todo
import com.omar.route_todo_application.ui.activities.UpdateTodo


class TodosAdapter(private val context: Context, private var Items:List<Todo>) : RecyclerView.Adapter<TodosAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context),
            parent, false
            ))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.todoItemTitle.text = Items[position].name
        holder.binding.todoItemDesc.text = Items[position].details


    }

    override fun getItemCount(): Int = Items.size

    @SuppressLint("NotifyDataSetChanged")
    fun reloadTasks(newItems: List<Todo>){
        Items = newItems
        notifyDataSetChanged() // notify adapter that data has been changed
    }


    inner class ViewHolder(val binding:ItemTodoBinding):RecyclerView.ViewHolder(binding.root)
    {
        val todo  = Todo()

        init {
           binding.root.setOnClickListener{
               val intent = Intent(context, UpdateTodo::class.java)
               context.startActivity(intent)
               // I want to know if the item click happened
               Log.i("VH", "The Item is clicked")
           }
        }
    }
}