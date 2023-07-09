package com.itis.taskmanager

import android.content.Context
import android.content.res.Resources.Theme
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.res.ResourcesCompat.ThemeCompat
import com.itis.taskmanager.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var binding: FragmentSettingsBinding? = null
    private var currentLocale: Locale = Locale.getDefault()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        val preferenceHelper = PreferenceHelper(requireContext())

        // Изменение языка
        binding!!.butrus.setOnClickListener{
            currentLocale = Locale("ru")
            setCurrentLocale()
            requireActivity().recreate()
            preferenceHelper.selectedLanguage = "ru"
        }
        binding!!.buteng.setOnClickListener{
            currentLocale = Locale("en")
            setCurrentLocale()
            requireActivity().recreate()
            preferenceHelper.selectedLanguage = "en"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private fun setCurrentLocale(){
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(currentLocale)
        resources.updateConfiguration(configuration,resources.displayMetrics)
    }
}