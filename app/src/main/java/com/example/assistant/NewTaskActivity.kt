package com.example.assistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class NewTaskActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var cancelButton: TextView
    private lateinit var addButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        supportActionBar?.hide()

        initButtons()
        setButtonsListeners()
    }

    private fun initButtons() {
        backButton = findViewById(R.id.newBackButton)
        cancelButton = findViewById(R.id.newCancelButton)
        addButton = findViewById(R.id.newAddButton)
    }

    private fun setButtonsListeners() {
        backButton.setOnClickListener {
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
        addButton.setOnClickListener {
            //guardar datos
            finish()
        }
    }
}