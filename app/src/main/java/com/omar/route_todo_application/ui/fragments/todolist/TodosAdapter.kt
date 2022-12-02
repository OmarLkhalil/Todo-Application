package com.omar.route_todo_application.ui.fragments.todolist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omar.route_todo_application.databinding.ItemTodoBinding
import com.omar.route_todo_application.models.Todo
import com.zerobranch.layout.SwipeLayout


class TodosAdapter(private var Items:List<Todo>) : RecyclerView.Adapter<TodosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context),
            parent, false
            ))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.todoItemTitle.text = Items[position].name
        holder.binding.todoItemDesc.text = Items[position].details

        holder.binding.swipeLayout
            .setOnActionsListener(object :SwipeLayout.SwipeActionsListener{
                override fun onClose() {
                }

                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if(direction == SwipeLayout.RIGHT){
                        holder.binding.delete.setOnClickListener{
                            onDeleteClickListener?.onItemClick(position,Items[position])
                        }
                    }
                    else if(direction==SwipeLayout.LEFT){
                    }
                }
            })
    }

    override fun getItemCount(): Int = Items.size

    @SuppressLint("NotifyDataSetChanged")
    fun reloadTasks(newItems: List<Todo>){
        Items = newItems
        notifyDataSetChanged() // notify adapter that data has been changed
    }


    var onDeleteClickListener:OnItemClickListener? =null
    interface OnItemClickListener{
        fun onItemClick(pos:Int,item:Todo)
    }

    class ViewHolder(val binding:ItemTodoBinding):RecyclerView.ViewHolder(binding.root)

}