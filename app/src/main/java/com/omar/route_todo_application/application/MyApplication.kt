package com.omar.route_todo_application.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.omar.route_todo_application.R
import com.omar.route_todo_application.databinding.ActivityMainBinding
import com.omar.route_todo_application.ui.fragments.AddToDoBottomSheet
import com.omar.route_todo_application.ui.fragments.SettingsFragment
import com.omar.route_todo_application.ui.fragments.TodoList.TodoListFragment

class MyApplication : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.fab.setOnClickListener{
            showAddBottomSheet()
        }

        binding.bottomNavigation.setOnItemSelectedListener(
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

        binding.bottomNavigation.selectedItemId = R.id.menu_list
    }

    private fun showAddBottomSheet() {
        val addBottomSheet = AddToDoBottomSheet()
        addBottomSheet.show(supportFragmentManager, "")
    }

    private fun pushFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}