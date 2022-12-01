package com.omar.route_todo_application.ui.fragments.TodoList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.omar.route_todo_application.R
import com.omar.route_todo_application.databinding.ItemTodoBinding
import com.omar.route_todo_application.models.Todo


class TodosAdapter(private var Items:List<Todo>) : RecyclerView.Adapter<TodosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: ItemTodoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todo,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Items[position]
            holder.bind(item)
    }

    override fun getItemCount(): Int {
        return Items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadTasks(newItems: List<Todo>){
        Items = newItems
        notifyDataSetChanged() // notify adapter that data has been changed
    }

    inner class ViewHolder(var binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo){
            binding.todoItem = todo
            binding.invalidateAll()
        }
    }
}