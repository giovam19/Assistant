package com.example.assistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.assistant.Controller.ItemListController
import com.example.assistant.Model.ListItem
import com.example.assistant.Model.List as OList
import java.util.Date

class NewListActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var saveButton: ImageView
    private lateinit var addItemButton: TextView

    private lateinit var title: EditText
    private lateinit var listContainer: LinearLayout
    private lateinit var listItemControllers: MutableList<ItemListController>
    private lateinit var listItem: MutableList<ListItem>
    private lateinit var errorMsg: TextView

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
        addItemButton = findViewById(R.id.newListAddItem)
        title = findViewById(R.id.newListTitle)
        errorMsg = findViewById(R.id.listErrorMsg)
        listContainer = findViewById(R.id.newListContainer)

        listItemControllers = mutableListOf()
        listItem = mutableListOf()
    }

    private fun setButtonsListeners() {
        backButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            val list = prepareData()
            if (list != null) {
                //guardar en bbdd

                println(list.title)
                println(list.elements.size)
                for (item in list.elements) {
                    println("check: ${item.checked}; text: ${item.element}")
                }
                Toast.makeText(this, "List Saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        addItemButton.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val newItem: View = inflater.inflate(R.layout.check_list_item, listContainer, false)

            val itemController = ItemListController(this, newItem, listContainer, listItemControllers)
            listItemControllers.add(itemController)

            listContainer.addView(newItem)
        }
    }

    private fun prepareData(): OList? {
        for (item in listItemControllers) {
            val it = ListItem(item.getChecked(), item.getText())
            listItem.add(it)
        }
        val list = OList("", title.text.toString(), listItem, Date(), false)
        val msg = list.IsValidData()

        if (msg.isNotEmpty()) {
            errorMsg.text = msg
            errorMsg.visibility = TextView.VISIBLE

            return null
        } else {
            errorMsg.text = ""
            errorMsg.visibility = TextView.GONE

            return list
        }
    }
}