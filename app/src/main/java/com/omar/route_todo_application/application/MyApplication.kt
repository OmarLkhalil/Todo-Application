package com.omar.route_todo_application.application

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.omar.route_todo_application.R
import com.omar.route_todo_application.base.BaseApplication
import com.omar.route_todo_application.databinding.ActivityMainBinding
import com.omar.route_todo_application.ui.fragments.addtodo.AddToDoBottomSheet
import com.omar.route_todo_application.ui.fragments.settings.SettingsFragment
import com.omar.route_todo_application.ui.fragments.todolist.TodoListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MyApplication : BaseApplication() {

    private lateinit var  binding: ActivityMainBinding
    private lateinit var  navCtrl: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.addButton.setOnClickListener {
            showAddBottomSheet()
        }

        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navCtrl         = navHostFrag.navController


        binding.bottomNavView.setupWithNavController(navCtrl)
        Navigation.setViewNavController(binding.bottomNavView, navCtrl)
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

}