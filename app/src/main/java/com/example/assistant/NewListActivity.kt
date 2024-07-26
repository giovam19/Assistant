package com.example.assistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.assistant.Controller.ItemListController
import com.example.assistant.Model.ListItem

class NewListActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var saveButton: ImageView
    private lateinit var addItem: TextView

    private lateinit var listContainer: LinearLayout
    private var listItemControllers = mutableListOf<ItemListController>()
    private lateinit var listItem: List<ListItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_list)
        supportActionBar?.hide()

        initComponents()
        setButtonsListeners()
    }

    private fun initComponents() {
        backButton = findViewById(R.id.newBackButton)
        saveButton = findViewById(R.id.newSaveButton)
        addItem = findViewById(R.id.newListAddItem)
        listContainer = findViewById(R.id.newListContainer)
    }

    private fun setButtonsListeners() {
        backButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            //guardar datos
            for (item in listItemControllers) {
                println("check: ${item.getChecked()}; text: ${item.getText()}")
            }
            finish()
        }
        addItem.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val newItem: View = inflater.inflate(R.layout.check_list_item, listContainer, false)

            val itemController = ItemListController(this, newItem, listContainer)
            listItemControllers.add(itemController)

            listContainer.addView(newItem)
        }
    }
}