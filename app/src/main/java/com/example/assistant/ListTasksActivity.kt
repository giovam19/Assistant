package com.example.assistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assistant.DataFactory.TestData
import com.example.assistant.ListHelpers.ListItemDecorator
import com.example.assistant.ListHelpers.TaskListAdapter

class ListTasksActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var nothing: TextView
    private lateinit var list: RecyclerView
    private lateinit var itemDecoration : ListItemDecorator
    private lateinit var adapter : TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tasks)
        supportActionBar?.hide()

        initComponents()
        setButtonsListeners()
    }

    override fun onStart() {
        super.onStart()

        setList()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.taskBackButton)
        nothing = findViewById(R.id.nothing_task)
        list = findViewById(R.id.tasksList)
        itemDecoration = ListItemDecorator(64)

        list.layoutManager = LinearLayoutManager(this)
        list.addItemDecoration(itemDecoration)
    }

    private fun setButtonsListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setList() {
        val lista = TestData.tasks
        //lista.sortByDescending { it.date }

        if (lista.isEmpty()) {
            list.visibility = RecyclerView.GONE
            nothing.visibility = TextView.VISIBLE
        } else {
            list.visibility = RecyclerView.VISIBLE
            nothing.visibility = TextView.GONE

            adapter = TaskListAdapter(lista, this)
            list.adapter = adapter
        }
    }
}