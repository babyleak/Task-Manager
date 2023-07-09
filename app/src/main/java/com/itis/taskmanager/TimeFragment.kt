package com.itis.taskmanager

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil.setContentView
import com.itis.taskmanager.databinding.FragmentTimeBinding


class TimeFragment : Fragment(R.layout.fragment_time) {
    private lateinit var binding: FragmentTimeBinding
    private var timer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            bStart.setOnClickListener {
                startCountDownTimer(20000)
            }
        }
    }

    private fun startCountDownTimer(timeMillis: Long){
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1){
            override fun onTick(timeM: Long) {
                binding.textView3.text = timeM.toString()
            }

            override fun onFinish() {
                binding.textView3.text = "Finish"
            }

        }.start()
    }
    private fun setContentView(root: FrameLayout) {
    }
}