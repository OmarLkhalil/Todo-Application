package com.omar.route_todo_application.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.omar.route_todo_application.R
import com.omar.route_todo_application.clearTime
import com.omar.route_todo_application.database.MyDatabase
import com.omar.route_todo_application.databinding.FragmentAddTodoBinding
import com.omar.route_todo_application.models.Todo
import java.util.Calendar

class AddToDoBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAddTodoBinding
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }
    @SuppressLint("SetTextI18n")
    private fun initViews(view: View){

        binding.chooseDate.setOnClickListener {
            showDate()
        }

        binding.chooseDate.text = (
                ""      +(calendar.get(Calendar.MONTH)+1)
                        +"/"
                        +calendar.get(Calendar.DAY_OF_MONTH)+"/"
                        +calendar.get(Calendar.YEAR))

        binding.buttonAddtodo.setOnClickListener {
            if(validate()){
                // Is valid and insert Todo Item
                insertTodo(binding.todoTitle.editText.toString(), binding.todoDetails.editText.toString())
            }
        }
    }

    private fun insertTodo(title: String?, details: String?) {
        val todo = Todo(
            name    = title,
            details = details,
            date    = calendar.clearTime().time,
            isDone  = false
        )
        MyDatabase.getInstance(requireContext())
            .todoDao()
            .addTodo(todo)
        Toast.makeText(requireContext(), "Todo added successfully", Toast.LENGTH_LONG).show()
        dismiss()
    }

    private fun validate(): Boolean{

        var isValid = true

        if(binding.todoTitle.editText?.text.toString().isBlank()){
            binding.todoTitle.error = "Please enter a title"
            isValid = false
        }
        else {
            binding.todoTitle.error = null
        }

        if(binding.todoDetails.editText?.text.toString().isBlank()){
            binding.todoDetails.error = "Please enter a details"
            isValid = false
        }
        else
        {
            binding.todoDetails.error = null
        }
        return isValid
    }
    @SuppressLint("SetTextI18n")

    fun showDate(){
        val context    = requireContext()
        val datePicker = DatePickerDialog(context,

            { _, year, month, day ->
                calendar.set(Calendar.DAY_OF_MONTH, day)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.YEAR, year)
                binding.chooseDate.text = ""+day+ "/" +(month+1)+ "/" +year
            },

            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}