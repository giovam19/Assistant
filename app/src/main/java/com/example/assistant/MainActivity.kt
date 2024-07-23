package com.example.assistant

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assistant.DataFactory.TestData
import com.example.assistant.ListHelpers.ListAdapter
import com.example.assistant.ListHelpers.ListItemDecorator


class MainActivity : AppCompatActivity() {
    private lateinit var profileB: ImageView
    private lateinit var tasksB: ConstraintLayout
    private lateinit var birthsB: ConstraintLayout
    private lateinit var notesB: ConstraintLayout
    private lateinit var listsB: ConstraintLayout
    private lateinit var favsB: ConstraintLayout
    private lateinit var addB: ImageView

    private lateinit var newTask : ConstraintLayout
    private lateinit var newBirthday : ConstraintLayout
    private lateinit var newNote : ConstraintLayout
    private lateinit var newList : ConstraintLayout
    private lateinit var cancelNew : ImageView

    private lateinit var tasksNum: TextView
    private lateinit var birthsNum: TextView
    private lateinit var notesNum: TextView
    private lateinit var listsNum: TextView
    private lateinit var favsNum: TextView

    private lateinit var todayList: RecyclerView
    private lateinit var itemDecoration : ListItemDecorator
    private lateinit var adapter : ListAdapter
    private lateinit var nothing: TextView

    private var tasksNumI = 15
    private var birthsNumI = 30
    private var notesNumI = 10
    private var listsNumI = 3
    private var favsNumI = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        initButtons()
        initNums()
        setButtonsListeners()
        configList()
        setList()
    }

    override fun onStart() {
        super.onStart()
        addB.visibility = ImageView.VISIBLE
    }

    private fun initButtons() {
        profileB = findViewById(R.id.profileButton)
        tasksB = findViewById(R.id.tasksContainer)
        birthsB = findViewById(R.id.birthsContainer)
        notesB = findViewById(R.id.notesContainer)
        listsB = findViewById(R.id.listsContainer)
        favsB = findViewById(R.id.favsContainer)
        addB = findViewById(R.id.addButton)
    }

    private fun setButtonsListeners() {
        profileB.setOnClickListener {
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
        }
        tasksB.setOnClickListener {
            tasksNum.text = (++tasksNumI).toString()
        }
        birthsB.setOnClickListener {
            birthsNum.text = (++birthsNumI).toString()
        }
        notesB.setOnClickListener {
            notesNum.text = (++notesNumI).toString()
        }
        listsB.setOnClickListener {
            listsNum.text = (++listsNumI).toString()
        }
        favsB.setOnClickListener {
            favsNum.text = (++favsNumI).toString()
        }
        addB.setOnClickListener {
            //Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()
            addB.visibility = ImageView.INVISIBLE
            showPopupNewMenu(findViewById(R.id.activity_main))
        }
    }

    private fun initNums() {
        tasksNum  = findViewById(R.id.tasksNum)
        birthsNum = findViewById(R.id.birthsNum)
        notesNum = findViewById(R.id.notesNum)
        listsNum = findViewById(R.id.listsNum)
        favsNum = findViewById(R.id.favsNum)

        tasksNum.text = tasksNumI.toString()
        birthsNum.text = birthsNumI.toString()
        notesNum.text = notesNumI.toString()
        listsNum.text = listsNumI .toString()
        favsNum.text = favsNumI.toString()
    }

    private fun configList() {
        todayList = findViewById(R.id.todayList)
        nothing = findViewById(R.id.nothing_today)
        itemDecoration = ListItemDecorator()

        todayList.layoutManager = LinearLayoutManager(this)
        todayList.addItemDecoration(itemDecoration)
    }

    private fun setList() {
        val lista = TestData().todayItems()

        if (lista.isEmpty()) {
            todayList.visibility = RecyclerView.GONE
            nothing.visibility = TextView.VISIBLE
        } else {
            todayList.visibility = RecyclerView.VISIBLE
            nothing.visibility = TextView.GONE
            adapter = ListAdapter(lista)
            todayList.adapter = adapter
        }
    }

    private fun showPopupNewMenu(parentView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.new_objects_popup, null)

        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0)

        initPopupButtons(popupView)
        setPopupButtonsListeners(popupWindow)
    }

    private fun initPopupButtons(view: View) {
        newTask = view.findViewById(R.id.newTask_Button)
        newBirthday = view.findViewById(R.id.newBirth_Button)
        newNote = view.findViewById(R.id.newNote_Button)
        newList = view.findViewById(R.id.newList_Button)
        cancelNew = view.findViewById(R.id.cancelNew_Button)
    }

    private fun setPopupButtonsListeners(popupWindow: PopupWindow) {
        newTask.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss()
        }
        newBirthday.setOnClickListener {
            val intent = Intent(this, NewBirthdayActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss()
        }
        newNote.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss()
        }
        newList.setOnClickListener {
            val intent = Intent(this, NewListActivity::class.java)
            startActivity(intent)
            popupWindow.dismiss()
        }
        cancelNew.setOnClickListener {
            popupWindow.dismiss()
            addB.visibility = ImageView.VISIBLE
        }
    }
}