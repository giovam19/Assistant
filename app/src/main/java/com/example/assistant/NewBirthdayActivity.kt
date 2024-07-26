package com.example.assistant

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.assistant.Model.Birthday
import com.example.assistant.Model.Task
import java.time.Month
import java.util.Calendar

class NewBirthdayActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var saveButton: ImageView

    private lateinit var nameText: EditText
    private lateinit var dateButton: ConstraintLayout
    private lateinit var dateText: TextView
    private lateinit var errorMsg: TextView
    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0

    private lateinit var calendarView: ConstraintLayout
    private lateinit var textMonth: TextView
    private lateinit var textDay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_birthday)
        supportActionBar?.hide()

        initComponents()
        setButtonsListeners()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.newBackButton)
        saveButton = findViewById(R.id.newSaveButton)

        nameText = findViewById(R.id.newBirthName)
        dateButton = findViewById(R.id.newBirthDate)
        dateText = findViewById(R.id.newDateTW)
        errorMsg = findViewById(R.id.birthErrorMsg)

        calendarView = findViewById(R.id.newBirthdayCalendar)
        textMonth = findViewById(R.id.newBirthMonth)
        textDay = findViewById(R.id.newBirthDay)
    }

    private fun setButtonsListeners() {
        backButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            val birth = prepareData()
            if (birth != null) {
                //guardar en bbdd

                println(birth.name)
                println(birth.date)
                Toast.makeText(this, "Birthday Saved!", Toast.LENGTH_SHORT).show()
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

                setCalendarView(selectedDay, selectedMonth+1, selectedYear)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun setCalendarView(day: Int, month: Int, year: Int) {
        this.day = day
        this.month = month
        this.year = year

        if (day < 10)
            textDay.text = "0$day"
        else
            textDay.text = day.toString()

        textMonth.text = NumMonthToText(month)
        calendarView.visibility = ConstraintLayout.VISIBLE
    }

    private fun NumMonthToText(month: Int): String {
        return when (month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> ""
        }
    }

    private fun prepareData(): Birthday? {
        val birth = Birthday("", nameText.text.toString(), dateText.text.toString(), false)
        val msg = birth.IsValidData()

        if (msg.isNotEmpty()) {
            errorMsg.text = msg
            errorMsg.visibility = TextView.VISIBLE

            return null
        } else {
            errorMsg.text = ""
            errorMsg.visibility = TextView.GONE

            return birth
        }
    }
}