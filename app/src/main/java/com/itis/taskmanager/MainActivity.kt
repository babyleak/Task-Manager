package com.itis.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCurrentLocales()
        val controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController
        findViewById<BottomNavigationView>(R.id.bnv_main).apply {
            setupWithNavController(controller)
        }

    }
    private fun setCurrentLocales(){
        val preferenceHelper = PreferenceHelper(this)
        val currentLocales = Locale(preferenceHelper.selectedLanguage.toString())
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(currentLocales)
        resources.updateConfiguration(configuration,resources.displayMetrics)

    }
}