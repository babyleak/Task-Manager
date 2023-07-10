package com.itis.taskmanager

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itis.taskmanager.databinding.FragmentTimeBinding

class CountTimeTimer : Fragment() {
    private lateinit var countDownTimer: CountDownTimer
    private var _binding: FragmentTimeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            val timeInSeconds = binding.etTime.text.toString().toLong()
            startTimer(timeInSeconds * 1000)
        }

        binding.btnStop.setOnClickListener {
            stopTimer()
        }
    }

    private fun startTimer(timeInMillis: Long) {
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00"
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
