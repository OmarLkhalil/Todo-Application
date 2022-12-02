package com.omar.route_todo_application.application

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.omar.route_todo_application.R
import com.omar.route_todo_application.base.BaseApplication
import com.omar.route_todo_application.databinding.ActivityMainBinding
import com.omar.route_todo_application.ui.fragments.AddToDoBottomSheet
import com.omar.route_todo_application.ui.fragments.SettingsFragment
import com.omar.route_todo_application.ui.fragments.todolist.TodoListFragment

class MyApplication : BaseApplication() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.addButton.setOnClickListener{
            showAddBottomSheet()
        }

        binding.bottomNavigationView.setOnItemSelectedListener(
            NavigationBarView.OnItemSelectedListener {
                    item->
                if(item.itemId == R.id.menu_list)
                {
                    pushFragment(TodoListFragment())
                }
                else if (item.itemId == R.id.menu_settings)
                {
                    pushFragment(SettingsFragment())
                }
                return@OnItemSelectedListener true
            }
        )

        binding.bottomNavigationView.selectedItemId = R.id.menu_list
    }

    private fun showAddBottomSheet() {
        val addBottomSheet = AddToDoBottomSheet()
        addBottomSheet.taskAddedLister =
            object: AddToDoBottomSheet.OnTaskAddedListener{
                override fun onTaskAdded() {

                    val fragment = supportFragmentManager
                        .findFragmentByTag(TodoListFragment.TAG)

                    if(fragment!= null)
                    {
                        (fragment as TodoListFragment).reloadTasks()
                    }
                }
            }
        addBottomSheet.show(supportFragmentManager, null)
    }

    private fun pushFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}