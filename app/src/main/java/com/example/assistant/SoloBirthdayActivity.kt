package com.example.assistant

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.assistant.DataFactory.TestData
import com.example.assistant.Model.Birthday
import com.example.assistant.Model.Task
import java.util.Calendar

class SoloBirthdayActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var optionsButton: ImageView
    private lateinit var name: EditText
    private lateinit var day: TextView
    private lateinit var month: TextView
    private lateinit var date: ConstraintLayout
    private lateinit var dateText: TextView
    private lateinit var saveEditButton: ImageView

    private lateinit var favoriteButton: LinearLayout
    private lateinit var favoriteIcon: ImageView
    private lateinit var editButton: LinearLayout
    private lateinit var deleteButton: LinearLayout

    private lateinit var birthday: Birthday
    private lateinit var id: String
    private var edit: Boolean = false

    private lateinit var oldName: String
    private lateinit var oldDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solo_birthday)

        supportActionBar?.hide()

        initComponents()
        setButtonListeners()
        setViewValues()
        setEditableForm()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.birthBackButton)
        optionsButton = findViewById(R.id.birthOptionsButton)
        saveEditButton = findViewById(R.id.editSaveButton)

        name = findViewById(R.id.birthName)
        day = findViewById(R.id.birthDay)
        month = findViewById(R.id.birthMonth)
        date = findViewById(R.id.newBirthDate)
        dateText = findViewById(R.id.newDateTW)

        id = intent.getStringExtra("uuid")!!
        birthday = TestData.births.find { it.id == id }!!
    }

    private fun setButtonListeners() {
        backButton.setOnClickListener {
            if (edit) {
                edit = false
                setOldData()
                setEditableForm()
            } else {
                finish()
            }
        }
        optionsButton.setOnClickListener {
            showPopupOptions(findViewById(R.id.activity_solo_birthday))
        }
        saveEditButton.setOnClickListener {
            edit = false
            saveNewData()
            setEditableForm()
        }
        date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { view, selectedYear, selectedMonth, selectedDay ->
                val dia = if (selectedDay < 10) "0$selectedDay" else selectedDay.toString()
                val mes = if (selectedMonth+1 < 10) "0${selectedMonth+1}" else (selectedMonth+1).toString()
                val fechaSeleccionada = "$dia/$mes/$selectedYear"
                dateText.text = fechaSeleccionada

                setCalendarView(dia, mes)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun setCalendarView(dia: String, mes: String) {
        day.text = dia
        month.text = getTextMonth(mes)
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

    private fun setViewValues() {
        name.setText(birthday.name)
        setCalendarView(birthday.getDay(), birthday.getMonth())
        dateText.text = birthday.date
    }

    private fun setEditableText(text: EditText, editable: Boolean) {
        text.isFocusable = editable
        text.isFocusableInTouchMode = editable
        text.isClickable = editable
        text.isLongClickable = editable
    }

    private fun setEditableForm() {
        saveEditButton.visibility = if (edit) ImageView.VISIBLE else ImageView.GONE
        optionsButton.visibility = if (!edit) ImageView.VISIBLE else ImageView.GONE

        setEditableText(name, edit)

        date.isClickable = edit
        date.isEnabled = edit
        date.visibility = if (edit) ConstraintLayout.VISIBLE else ConstraintLayout.GONE
    }

    private fun keepOldData() {
        oldName = name.text.toString()
        oldDate = dateText.text.toString()
    }

    private fun setOldData() {
        name.setText(oldName)
        dateText.text = oldDate

        val pieces = oldDate.split('/')
        setCalendarView(pieces[0], pieces[1])

    }

    private fun saveNewData() {
        //guardar en bdd

        birthday.name = name.text.toString()
        birthday.date = dateText.text.toString()
    }

    private fun setFavoriteValue() {
        val dirty = !birthday.isFavorite
        //guardar en bdd

        birthday.isFavorite = dirty
    }

    private fun showPopupOptions(parentView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_options, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.showAsDropDown(optionsButton, -380, -100)

        initPopupButtons(popupView)
        setPopupButtonsListeners(popupWindow)
    }

    private fun initPopupButtons(popupView: View) {
        favoriteButton = popupView.findViewById(R.id.favButton)
        favoriteIcon = popupView.findViewById(R.id.favoriteIcon)
        editButton = popupView.findViewById(R.id.editButton)
        deleteButton = popupView.findViewById(R.id.removeButton)

        if (birthday.isFavorite)
            favoriteIcon.setImageResource(R.drawable.favorite_fill)
        else
            favoriteIcon.setImageResource(R.drawable.favorite_empty)
    }

    private fun setPopupButtonsListeners(popupWindow: PopupWindow) {
        favoriteButton.setOnClickListener {
            setFavoriteValue()

            if (birthday.isFavorite)
                favoriteIcon.setImageResource(R.drawable.favorite_fill)
            else
                favoriteIcon.setImageResource(R.drawable.favorite_empty)
        }
        editButton.setOnClickListener {
            edit = true
            setEditableForm()
            keepOldData()
            popupWindow.dismiss()
        }
        deleteButton.setOnClickListener {
            val dirty = birthday
            //eliminar de bdd
            TestData.births.remove(dirty)
            Toast.makeText(this, "Birthday Deleted!", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            finish()
        }
    }
}