package com.itis.taskmanager

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper (context: Context) {
    private val prefsName = "MyPrefs"
    private val prefSelectedLanguage = "en"
    var isDarkTheme:Boolean = false
    private val sharedPreferences:SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    var selectedLanguage: String? get() = sharedPreferences.getString(prefSelectedLanguage, null)
        set (value) = sharedPreferences.edit().putString(prefSelectedLanguage, value).apply()

}