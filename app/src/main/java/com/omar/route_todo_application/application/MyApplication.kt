package com.omar.route_todo_application.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.omar.route_todo_application.R
import com.omar.route_todo_application.ui.activities.AddTodoActivity
import com.omar.route_todo_application.ui.fragments.SettingsFragment
import com.omar.route_todo_application.ui.fragments.TodoListFragment

class MyApplication : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var addFab   : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        addFab    = findViewById(R.id.fab)

        addFab.setOnClickListener{
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

        bottomNav.setOnItemSelectedListener(
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

        bottomNav.selectedItemId = R.id.menu_list
    }

    private fun pushFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}