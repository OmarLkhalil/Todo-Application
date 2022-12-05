package com.omar.route_todo_application.ui.fragments.todolist

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omar.route_todo_application.R
import com.omar.route_todo_application.base.BaseTodoList
import com.omar.route_todo_application.database.MyDatabase
import com.omar.route_todo_application.databinding.FragmentTodoListBinding
import com.omar.route_todo_application.models.Todo
import java.util.*

class TodoListFragment: BaseTodoList(){

    private lateinit var todosList : List<Todo>
    private lateinit var adapter   : TodosAdapter
    private lateinit var binding   : FragmentTodoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list
            , container
            , false)

        Log.i("OnCreateTodoListFragment", " OnCreate is Started")
        return binding.root
    }


    private val currentDate: Calendar = Calendar.getInstance();

    // I need current date of the day and months maybe weeks ( I don't care for hrs, mins, secs)
    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         todosList = MyDatabase.getInstance(requireContext())
            .todoDao().getTodosByDate(currentDate.time)
        adapter = TodosAdapter(requireContext(), todosList)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation      = LinearLayoutManager.VERTICAL
        binding.todosRec.layoutManager = layoutManager
        binding.todosRec.adapter       = adapter


        binding.calenderView.setOnDateChangedListener { _, selectedDate, selected ->
            // when user clicks on date
            currentDate.set(Calendar.MONTH,selectedDate.month-1)
            currentDate.set(Calendar.DAY_OF_MONTH,selectedDate.day)
            currentDate.set(Calendar.YEAR,selectedDate.year)
            reloadTasks()

            Log.i("OnViewCreated", "On View Created is Started")
        }


        itemTouchHelper.attachToRecyclerView(binding.todosRec)
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            // This method gets called when user pull for refresh,
            // You can make your API call here,
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (binding.refreshLayout.isRefreshing) {
                    binding.refreshLayout.isRefreshing = false
                    reloadTasks()
                }
            }, 1000)
        }
    }


    fun reloadTasks(){
        todosList = MyDatabase.getInstance(requireContext())
            .todoDao()
            .getTodosByDate(currentDate.time)
        adapter.reloadTasks(todosList)

        // I want to know when the tasks is reloaded
        Log.i("R", "tasks reloaded now")
    }


    fun deleteTodo(position: Int, todo: Todo){

        // When user swipe the item it will show a message for him if he wants to delete the item
        showMessage("Are you sure you want to delete this task ?",
            posActionTitle = "yes", // If user clicked yes
            posAction = { dialogInterface, i ->
                dialogInterface?.dismiss()
                MyDatabase.getInstance(requireContext())
                    .todoDao()
                    .deleteTodo(todo) // It will be deleted from database ( we show only tasks in the database)
                        },
            // If user clicked cancel
            negActionTitle = "cancel",
            // We will dismiss the dialog
            negAction = DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface?.dismiss()
                // we need fun undoDelete so it get the item back after swapping without deleting
                undoDelete(position, todo )
            }
        )
    }

    override fun onResume() {
        super.onResume()
        reloadTasks()
        Log.i("On Resume", "On Resume is Started")
    }


    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // when item is dragged
            val fromPosition = viewHolder.adapterPosition
            val toPosition   = target.adapterPosition

            // user swap the dragged Item UP and DOWN position
            Collections.swap(todosList, fromPosition, toPosition)

            // swap the dragged Item in the recyclerView
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Called when swipe Item LEFT and RIGHT
            val postition     = viewHolder.adapterPosition
            deleteTodo(postition, todosList[postition])
        }
    })


    // when user clicked cancel button the item will back into the recyclerView
    private fun undoDelete(position: Int, todo: Todo){
        val deleteList = todosList.toMutableList()
        deleteList.add(position, todo)
        adapter.notifyItemInserted(position)
        adapter.notifyItemRangeChanged(position, deleteList.size)

    }

    companion object{
        val TAG = "Todos - Fragment"
    }
}