package com.example.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.itis.taskmanager.R
import com.itis.taskmanager.databinding.FragmentTimeBinding


class TimeFragment : Fragment(R.layout.fragment_time) {

    private var binding: FragmentTimeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimeBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}