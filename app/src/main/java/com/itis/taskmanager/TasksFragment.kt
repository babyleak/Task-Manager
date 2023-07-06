package com.itis.taskmanager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.itis.taskmanager.databinding.FragmentTasksBinding

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private var binding: FragmentTasksBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTasksBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}