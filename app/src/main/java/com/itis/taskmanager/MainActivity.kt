package com.itis.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController

        findViewById<BottomNavigationView>(R.id.bnv_main).apply {
            setupWithNavController(controller)
        }
    }
}