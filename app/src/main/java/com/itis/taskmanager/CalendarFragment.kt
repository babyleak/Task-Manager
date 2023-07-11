package com.itis.taskmanager

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itis.taskmanager.databinding.FragmentCalendarBinding
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


class CalendarFragment : Fragment(R.layout.fragment_calendar), CalendarAdapter.OnItemListener {
    private var binding: FragmentCalendarBinding? = null
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var selectedDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        binding?.btnBack?.setOnClickListener { prevMonthAction(view) }
        binding?.btnNext?.setOnClickListener { nextMonthAction(view) }

        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()
    }

    private fun initWidgets() {
        calendarRecyclerView = binding?.calendarRecyclerView
        monthYearText = binding?.monthYearTV
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        monthYearText!!.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMM yyyy", Locale("en"))
        return date!!.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun nextMonthAction(view: View?) {
        selectedDate = selectedDate!!.plusMonths(1)
        setMonthView()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun prevMonthAction(view: View?) {
        selectedDate = selectedDate!!.minusMonths(1)
        setMonthView()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, dayText: String?) {
        var msg: String = "You selected ${dayText} ${monthYearFromDate(selectedDate)}"
        Snackbar.make(binding!!.root, msg, Snackbar.LENGTH_LONG).show()
        // select date
    }
}