package com.omar.route_todo_application.ui.fragments.addtodo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.omar.route_todo_application.R
import com.omar.route_todo_application.base.BaseBottomSheet
import com.omar.route_todo_application.database.MyDatabase
import com.omar.route_todo_application.databinding.FragmentAddTodoBinding
import com.omar.route_todo_application.models.Todo
import java.util.Calendar

class AddToDoBottomSheet : BaseBottomSheet() {

    private lateinit var binding : FragmentAddTodoBinding
    private val currentDate: Calendar = Calendar.getInstance()

    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener{
            insertTodo()
        }
        binding.dateContainer.setOnClickListener{
            showDate()
        }
    }
    @SuppressLint("SetTextI18n")

    private fun insertTodo() {
        if(!validate())
            return
        showLoadingDialog()
        val todo = Todo(
            name    = binding.titleContainer.editText?.text.toString(),
            details = binding.descContainer.editText?.text.toString(),
            date    = currentDate.time,
            isDone  = false
        )
        MyDatabase.getInstance(requireContext())
            .todoDao()
            .addTodo(todo)
        hideLoading()
        showMessage("Todo Added Successfully",
            posActionTitle =  "ok", posAction = {
                dialogInterface, _ ->
                dialogInterface.dismiss()
                dismiss()
                taskAddedLister?.onTaskAdded()
            }, cancelable = false)
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

    @SuppressLint("SetTextI18n")
    fun showDate(){
        val context    = requireContext()
        val datePicker = DatePickerDialog(context,

            { _, year, month, day ->
                currentDate.set(Calendar.DAY_OF_MONTH, day)
                currentDate.set(Calendar.MONTH, month)
                currentDate.set(Calendar.YEAR, year)
                binding.date.text = ""+day+ "/" +(month+1)+ "/" +year
            },

            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    var taskAddedLister : OnTaskAddedListener?=null
    interface OnTaskAddedListener{
        fun onTaskAdded();
    }
}