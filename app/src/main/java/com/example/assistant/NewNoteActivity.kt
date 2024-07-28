package com.example.assistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.assistant.DataFactory.TestData
import com.example.assistant.Model.Note
import java.util.Date
import java.util.UUID

class NewNoteActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var saveButton: ImageView

    private lateinit var title: EditText
    private lateinit var note: EditText
    private lateinit var errorMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        supportActionBar?.hide()

        initComponents()
        setButtonsListeners()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.newBackButton)
        saveButton = findViewById(R.id.newSaveButton)

        title = findViewById(R.id.newNoteTitle)
        note = findViewById(R.id.newNote)
        errorMsg = findViewById(R.id.noteErrorMsg)
    }

    private fun setButtonsListeners() {
        backButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            val note = prepareData()
            if (note != null) {
                //guardar en bbdd
                TestData.notes.add(note)

                println(note.title)
                println(note.note)
                Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun prepareData(): Note? {
        val note = Note(UUID.randomUUID().toString(), title.text.toString(), note.text.toString(), Date(), false)
        val msg = note.IsValidData()

        if (msg.isNotEmpty()) {
            errorMsg.text = msg
            errorMsg.visibility = TextView.VISIBLE

            return null
        } else {
            errorMsg.text = ""
            errorMsg.visibility = TextView.GONE

            return note
        }
    }
}