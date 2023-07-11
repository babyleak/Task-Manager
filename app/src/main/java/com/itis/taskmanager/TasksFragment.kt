package com.itis.taskmanager
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AlertDialog

class TasksFragment : Fragment() {
    private lateinit var taskListView: ListView
    private lateinit var addTaskButton: Button
    private lateinit var deleteAllTasksButton: Button
    private lateinit var taskList: MutableList<Task>
    private lateinit var adapter: TaskAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        taskListView = view.findViewById(R.id.taskListView)
        addTaskButton = view.findViewById(R.id.addTaskButton)
        deleteAllTasksButton = view.findViewById(R.id.deleteAllTasksButton)

        taskList = mutableListOf()
        adapter = TaskAdapter(requireContext(), taskList)
        taskListView.adapter = adapter

        sharedPreferences = requireContext().getSharedPreferences("TaskManager", Context.MODE_PRIVATE)
        loadTasksFromSharedPreferences()

        deleteAllTasksButton.setOnClickListener {
            taskList.clear()
            adapter.notifyDataSetChanged()
            deleteAllTasksFromSharedPrefernces()
        }

        addTaskButton.setOnClickListener {
            openAddTaskDialog()
        }

        return view
    }

    private fun openAddTaskDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.add_task_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Установить ширину и высоту диалогового окна

        val addTaskButton = dialog.findViewById<Button>(R.id.addTaskDialogButton)
        val taskTitleEditText = dialog.findViewById<EditText>(R.id.taskTitleEditText)
        val taskDescriptionEditText = dialog.findViewById<EditText>(R.id.taskDescriptionEditText)
        val taskDatePicker = dialog.findViewById<DatePicker>(R.id.taskDatePicker)

        addTaskButton.setOnClickListener {
            val title = taskTitleEditText.text.toString()
            val description = taskDescriptionEditText.text.toString()

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val year = taskDatePicker.year
            val month = taskDatePicker.month
            val day = taskDatePicker.dayOfMonth
            val date = Calendar.getInstance().apply {
                set(year, month, day)
            }.time

            val task = Task(title, description, date)
            taskList.add(task)
            adapter.notifyDataSetChanged()

            saveTasksToSharedPreferences()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun saveTasksToSharedPreferences() {
        val editor = sharedPreferences.edit()
        val taskSet = taskList.map { task ->
            "${task.title}|${task.description}|${task.date.time}"
        }.toSet()
        editor.putStringSet("tasks", taskSet)
        editor.apply()
    }

    private fun deleteAllTasksFromSharedPrefernces() {
        val editor = sharedPreferences.edit()
        editor.remove("tasks")
        editor.apply()
    }

    private fun loadTasksFromSharedPreferences() {
        val taskSet = sharedPreferences.getStringSet("tasks", emptySet())
        taskList.clear()
        taskSet?.forEach { taskString ->
            val parts = taskString.split("|")
            if (parts.size == 3) {
                val title = parts[0]
                val description = parts[1]
                val date = Date(parts[2].toLong())
                val task = Task(title, description, date)
                taskList.add(task)
            }
        }
        adapter.notifyDataSetChanged()
    }
}

data class Task(val title: String, val description: String, val date: Date)

class TaskAdapter(private val context: Context, private val tasks: MutableList<Task>) : BaseAdapter() {
    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)

        val taskTitleTextView: TextView = view.findViewById(R.id.taskTitleTextView)
        val taskDateTextView: TextView = view.findViewById(R.id.taskDateTextView)
        val deleteTaskButton: Button = view.findViewById(R.id.deleteTaskButton)

        val task = tasks[position]
        taskTitleTextView.text = task.title
        taskDateTextView.text = "${task.date}"

        view.setOnClickListener {
            showTaskDetailsDialog(task)
        }

        deleteTaskButton.setOnClickListener {
            tasks.removeAt(position)
            notifyDataSetChanged()
            saveTasksToSharedPreferences()
        }

        return view
    }
    private fun saveTasksToSharedPreferences() {
        val sharedPreferences = context.getSharedPreferences("TaskManager", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val taskSet = tasks.map { task ->
            "${task.title}|${task.description}|${task.date.time}"
        }.toSet()
        editor.putStringSet("tasks", taskSet)
        editor.apply()
    }
    private fun showTaskDetailsDialog(task: Task) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.task_details_dialog, null)

        val titleTextView: TextView = dialogView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = dialogView.findViewById(R.id.dateTextView)
        val descriptionTextView: TextView = dialogView.findViewById(R.id.descriptionTextView)
        titleTextView.text = task.title
        dateTextView.text = task.date.toString()
        descriptionTextView.text = task.description

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }
}