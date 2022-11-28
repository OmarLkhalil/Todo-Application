package com.omar.route_todo_application.ui.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.omar.route_todo_application.R
import com.omar.route_todo_application.database.MyDatabase
import com.omar.route_todo_application.models.Todo
import java.util.Calendar

class AddToDoBottomSheet : BottomSheetDialogFragment() {

    private lateinit var buttonAdd     : Button
    private lateinit var titleLayout   : TextInputLayout
    private lateinit var detailsLayout : TextInputLayout
    private lateinit var chooseDate    : TextView


    private val calendar: Calendar = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.fragment_add_todo, container, false)
        initViews(view)
        return view
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViews(view: View){
        titleLayout   = view.findViewById(R.id.todo_title)
        detailsLayout = view.findViewById(R.id.todo_details)
        buttonAdd     = view.findViewById(R.id.button_addtodo)
        chooseDate    = view.findViewById(R.id.choose_date)

        chooseDate.setOnClickListener {
            showDate()
        }

        chooseDate.text = (
                ""      +(calendar.get(Calendar.MONTH)+1)
                        +"/"
                        +calendar.get(Calendar.DAY_OF_MONTH)+"/"
                        +calendar.get(Calendar.YEAR))

        buttonAdd.setOnClickListener {
            if(validate()){
                // Is valid and insert Todo Item
                val title = titleLayout.editText?.text?.toString()
                val details = detailsLayout.editText?.text?.toString()
                insertTodo(title, details)
            }
        }

    }

    private fun insertTodo(title: String?, details: String?) {
        var todo = Todo(
            name = title,
            details = details,
            date = calendar.time
        )
        MyDatabase.getInstance(
            requireContext().applicationContext
        )
            ?.todoDao()
            ?.addTodo(todo)
        Toast.makeText(requireContext(), "Todo added successfully", Toast.LENGTH_LONG).show()
        dismiss()
    }

    private fun validate(): Boolean{

        var isValid = true

        if(titleLayout.editText?.text.toString().isBlank()){
            titleLayout.error = "Please enter a title"
            isValid = false
        }
        else {
            titleLayout.error = null
        }

        if(detailsLayout.editText?.text.toString().isBlank()){
            detailsLayout.error = "Please enter a details"
            isValid = false
        }
        else
        {
            detailsLayout.error = null
        }
        return isValid
    }
    @SuppressLint("SetTextI18n")

    @RequiresApi(Build.VERSION_CODES.N)
    fun showDate(){
        val context    = requireContext()
        val datePicker = DatePickerDialog(context,
            { _, year, month, day ->
                calendar.set(Calendar.DAY_OF_MONTH, day)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.YEAR, year)
                chooseDate.text = ""+day+ "/" +(month+1)+ "/" +year
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}