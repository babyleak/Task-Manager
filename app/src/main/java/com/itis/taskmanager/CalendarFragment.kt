package com.itis.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.itis.taskmanager.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private var binding: FragmentCalendarBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}