package com.omar.route_todo_application.ui.fragments.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.omar.route_todo_application.R
import com.omar.route_todo_application.base.BaseTodoList
import com.omar.route_todo_application.database.MyDatabase
import com.omar.route_todo_application.databinding.FragmentTodoListBinding
import com.omar.route_todo_application.models.Todo
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

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
        return binding.root
    }


    private val currentDate: Calendar = Calendar.getInstance();
    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         todosList = MyDatabase.getInstance(requireContext())
            .todoDao().getTodosByDate(currentDate.time)
        adapter = TodosAdapter(todosList)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation      = LinearLayoutManager.VERTICAL
        binding.todosRec.layoutManager = layoutManager
        binding.todosRec.adapter = adapter

        binding.calenderView.setOnDateChangedListener { _, selectedDate, selected ->
            // when user clicks on date
            currentDate.set(Calendar.MONTH,selectedDate.month-1)
            currentDate.set(Calendar.DAY_OF_MONTH,selectedDate.day)
            currentDate.set(Calendar.YEAR,selectedDate.year)
            reloadTasks()
        }
        adapter.onDeleteClickListener = object : TodosAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, item: Todo) {
                deleteTodo(item)
            }
        }
        binding.calenderView.setDateSelected(CalendarDay.today(),true)

    }




    override fun onResume(){
        super.onResume()
        reloadTasks()
    }

    fun reloadTasks(){
        todosList = MyDatabase.getInstance(requireContext())
            .todoDao()
            .getTodosByDate(currentDate.time)
        adapter.reloadTasks(todosList)
    }


    fun deleteTodo(todo: Todo){

        showMessage("Are you sure you want to delete this task ?",
            posActionTitle = "yes",
            posAction = { dialogInterface, i ->
                dialogInterface?.dismiss()
                MyDatabase.getInstance(requireContext())
                    .todoDao()
                    .deleteTodo(todo)
                reloadTasks() },
            negActionTitle = "cancel",
            negAction = DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface?.dismiss()
            }
        )
    }

    companion object{
        val TAG = "Todos - Fragment"
    }
}