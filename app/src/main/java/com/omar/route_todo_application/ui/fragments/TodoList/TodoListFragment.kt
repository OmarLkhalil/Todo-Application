package com.omar.route_todo_application.ui.fragments.TodoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omar.route_todo_application.R
import com.omar.route_todo_application.clearTime
import com.omar.route_todo_application.database.MyDatabase
import com.omar.route_todo_application.databinding.FragmentTodoListBinding
import com.omar.route_todo_application.models.Todo
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Calendar

class TodoListFragment: Fragment(){

    private lateinit var todosList : List<Todo>
    private lateinit var adapter   : TodosAdapter
    private lateinit var binding   : FragmentTodoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)

        return binding.root
    }



    var currentDate: Calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         todosList = MyDatabase.getInstance(requireContext())
            .todoDao().getTodosByDate(currentDate.time)


        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.todosRec.layoutManager = layoutManager
        adapter = TodosAdapter(todosList)
        binding.todosRec.adapter = adapter

        binding.calenderView.setOnDateChangedListener { _, selectedDate, selected ->
            // when user clicks on date
            currentDate.set(Calendar.MONTH,selectedDate.month-1)
            currentDate.set(Calendar.DAY_OF_MONTH,selectedDate.day)
            currentDate.set(Calendar.YEAR,selectedDate.year)
            reloadTasks()
        }

        binding.calenderView.setDateSelected(CalendarDay.today(),true)

    }

    fun reloadTasks(){
        todosList = MyDatabase.getInstance(requireContext())
            .todoDao()
            .getTodosByDate(currentDate.time)
        adapter.reloadTasks(todosList)
    }

    override fun onResume(){
        super.onResume()
        reloadTasks()
    }


}