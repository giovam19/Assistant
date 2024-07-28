package com.example.assistant

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.assistant.DataFactory.TestData
import com.example.assistant.Model.Task
import java.util.Calendar
import java.util.Date
import java.util.UUID

class NewTaskActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var saveButton: ImageView

    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var dateButton: ConstraintLayout
    private lateinit var dateText: TextView
    private lateinit var hourButton: ConstraintLayout
    private lateinit var hourText: TextView
    private lateinit var errorMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        supportActionBar?.hide()

        initComponents()
        setButtonsListeners()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.newBackButton)
        saveButton = findViewById(R.id.newSaveButton)
        title = findViewById(R.id.newTaskTitle)
        description = findViewById(R.id.newTaskDescription)
        dateButton = findViewById(R.id.newTaskDate)
        dateText = findViewById(R.id.newDateTW)
        hourButton = findViewById(R.id.newTaskHour)
        hourText = findViewById(R.id.newHourTW)
        errorMsg = findViewById(R.id.taskErrorMsg)
    }

    private fun setButtonsListeners() {
        backButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            val task = prepareData()
            if (task != null) {
                //guardar en bbdd
                TestData.tasks.add(task)

                println(task.title)
                println(task.date)
                println(task.hour)
                Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                val dia = if (selectedDay < 10) "0$selectedDay" else selectedDay.toString()
                val mes = if (selectedMonth+1 < 10) "0${selectedMonth+1}" else (selectedMonth+1).toString()
                val fechaSeleccionada = "$dia/$mes/$selectedYear"
                dateText.text = fechaSeleccionada

                //this.day = selectedDay
                //this.month = selectedMonth+1
                //this.year = selectedYear
            }, year, month, day)

            datePickerDialog.show()
        }
        hourButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, selectedHour, selectedMinute ->
                val minuto = if (selectedMinute < 10) "0$selectedMinute" else selectedMinute.toString()
                val hora = if (selectedHour < 10) "0$selectedHour" else selectedHour.toString()
                val horaSeleccionada = "$hora:$minuto"
                hourText.text = horaSeleccionada

                //ejectTime = horaSeleccionada
            }, hour, minute, true)

            timePickerDialog.show()
        }
    }

    private fun prepareData(): Task? {
        val task = Task(UUID.randomUUID().toString(), title.text.toString(), description.text.toString(), dateText.text.toString(), hourText.text.toString(), false)
        val msg = task.IsValidData()

        if (msg.isNotEmpty()) {
            errorMsg.text = msg
            errorMsg.visibility = TextView.VISIBLE

            return null
        } else {
            errorMsg.text = ""
            errorMsg.visibility = TextView.GONE

            return task
        }
    }
}