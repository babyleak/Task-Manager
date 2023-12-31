package com.itis.taskmanager

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarViewHolder(itemView: View, onItemListener: CalendarAdapter.OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val dayOfMonth: TextView
    private val onItemListener: CalendarAdapter.OnItemListener

    init {
        dayOfMonth = itemView.findViewById<TextView>(R.id.cellDayText)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        Log.e("ALERT", "Clicked!")
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }
}