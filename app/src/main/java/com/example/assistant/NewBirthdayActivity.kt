package com.example.assistant

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.assistant.DataFactory.TestData
import com.example.assistant.Model.Birthday
import com.example.assistant.Model.Task
import java.time.Month
import java.util.Calendar
import java.util.UUID

class NewBirthdayActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var saveButton: ImageView

    private lateinit var nameText: EditText
    private lateinit var dateButton: ConstraintLayout
    private lateinit var dateText: TextView
    private lateinit var errorMsg: TextView

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
                TestData.births.add(birth)

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

                setCalendarView(dia, mes)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun setCalendarView(day: String, month: String) {
        textDay.text = day
        textMonth.text = getTextMonth(month)
        calendarView.visibility = ConstraintLayout.VISIBLE
    }

    private fun getTextMonth(month: String): String {
        return when (month) {
            "01" -> "January"
            "02" -> "February"
            "03" -> "March"
            "04" -> "April"
            "05" -> "May"
            "06" -> "June"
            "07" -> "July"
            "08" -> "August"
            "09" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> ""
        }
    }

    private fun prepareData(): Birthday? {
        val birth = Birthday(UUID.randomUUID().toString(), nameText.text.toString(), dateText.text.toString(), false)
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