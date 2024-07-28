package com.example.assistant

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.assistant.DataFactory.TestData
import com.example.assistant.Model.Task
import java.util.Calendar


class SoloTaskActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var optionsButton: ImageView
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var date: ConstraintLayout
    private lateinit var dateText: TextView
    private lateinit var hour: ConstraintLayout
    private lateinit var hourText: TextView
    private lateinit var saveEditButton: ImageView

    private lateinit var favoriteButton: LinearLayout
    private lateinit var favoriteIcon: ImageView
    private lateinit var editButton: LinearLayout
    private lateinit var deleteButton: LinearLayout

    private lateinit var task: Task
    private lateinit var id: String
    private var edit: Boolean = false

    private lateinit var oldTitle: String
    private lateinit var oldDescription: String
    private lateinit var oldDate: String
    private lateinit var oldHour: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solo_task)
        supportActionBar?.hide()

        initComponents()
        setButtonListeners()
        setViewValues()
        setEditableForm()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.taskBackButton)
        optionsButton = findViewById(R.id.taskOptionsButton)
        saveEditButton = findViewById(R.id.editSaveButton)

        title = findViewById(R.id.taskTitle)
        description = findViewById(R.id.taskDescription)
        date = findViewById(R.id.taskDate)
        dateText = findViewById(R.id.dateTW)
        hour = findViewById(R.id.taskHour)
        hourText = findViewById(R.id.hourTW)

        id = intent.getStringExtra("uuid")!!
        task = TestData.tasks.find { it.id == id }!!
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
            showPopupOptions(findViewById(R.id.activity_solo_task))
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

            }, year, month, day)

            datePickerDialog.show()
        }
        hour.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, selectedHour, selectedMinute ->
                val minuto = if (selectedMinute < 10) "0$selectedMinute" else selectedMinute.toString()
                val hora = if (selectedHour < 10) "0$selectedHour" else selectedHour.toString()
                val horaSeleccionada = "$hora:$minuto"
                hourText.text = horaSeleccionada

            }, hour, minute, true)

            timePickerDialog.show()
        }

    }

    private fun setViewValues() {
        title.setText(task.title)
        if (task.description.isNotEmpty())
            description.setText(task.description)

        dateText.text = task.date
        hourText.text = task.hour
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

        setEditableText(title, edit)
        setEditableText(description, edit)

        date.isClickable = edit
        date.isEnabled = edit
        hour.isClickable = edit
        hour.isEnabled = edit
    }

    private fun keepOldData() {
        oldTitle = title.text.toString()
        oldDescription = description.text.toString()
        oldDate = dateText.text.toString()
        oldHour = hourText.text.toString()
    }

    private fun setOldData() {
        title.setText(oldTitle)
        description.setText(oldDescription)
        dateText.text = oldDate
        hourText.text = oldHour
    }

    private fun saveNewData() {
        //guardar en bdd

        task.title = title.text.toString()
        task.description = description.text.toString()
        task.date = dateText.text.toString()
        task.hour = hourText.text.toString()
    }

    private fun setFavoriteValue() {
        val dirty = !task.isFavorite
        //guardar en bdd

        task.isFavorite = dirty
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

        if (task.isFavorite)
            favoriteIcon.setImageResource(R.drawable.favorite_fill)
        else
            favoriteIcon.setImageResource(R.drawable.favorite_empty)
    }

    private fun setPopupButtonsListeners(popupWindow: PopupWindow) {
        favoriteButton.setOnClickListener {
            setFavoriteValue()

            if (task.isFavorite)
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
            val dirty = task
            //eliminar de bdd
            TestData.tasks.remove(dirty)
            Toast.makeText(this, "Task Deleted!", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            finish()
        }
    }
}