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
import com.example.assistant.ListHelpers.TodayListAdapter
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
    private lateinit var adapter : TodayListAdapter
    private lateinit var nothing: TextView

    private var tasksNumI: Int = 0
    private var birthsNumI: Int = 0
    private var notesNumI: Int = 0
    private var listsNumI: Int = 0
    private var favsNumI: Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        initComponents()
        initNums()
        setButtonsListeners()
        configList()
    }

    override fun onStart() {
        super.onStart()

        setList()
        calcualteNumObjects()
        addB.visibility = ImageView.VISIBLE
    }

    private fun initComponents() {
        profileB = findViewById(R.id.profileButton)
        tasksB = findViewById(R.id.tasksContainer)
        birthsB = findViewById(R.id.birthsContainer)
        notesB = findViewById(R.id.notesContainer)
        listsB = findViewById(R.id.listsContainer)
        favsB = findViewById(R.id.favsContainer)
        addB = findViewById(R.id.addButton)

        TestData().fillTasks() //debug
        TestData().fillBirthdays() //debug
    }

    private fun calcualteNumObjects() {
        tasksNumI = TestData.tasks.size
        birthsNumI = TestData.births.size
        notesNumI = TestData.notes.size
        listsNumI = TestData.lists.size

        tasksNum.text = tasksNumI.toString()
        birthsNum.text = birthsNumI.toString()
        notesNum.text = notesNumI.toString()
        listsNum.text = listsNumI .toString()
        favsNum.text = favsNumI.toString()
    }

    private fun setButtonsListeners() {
        profileB.setOnClickListener {
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
        }
        tasksB.setOnClickListener {
            val intent = Intent(this, ListTasksActivity::class.java)
            startActivity(intent)
        }
        birthsB.setOnClickListener {
            val intent = Intent(this, ListBirthdaysActivity::class.java)
            startActivity(intent)
        }
        notesB.setOnClickListener {
            //notesNum.text = (++notesNumI).toString()
        }
        listsB.setOnClickListener {
            //listsNum.text = (++listsNumI).toString()
        }
        favsB.setOnClickListener {
            favsNum.text = (++favsNumI).toString()
        }
        addB.setOnClickListener {
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
    }

    private fun configList() {
        todayList = findViewById(R.id.todayList)
        nothing = findViewById(R.id.nothing_today)
        itemDecoration = ListItemDecorator(32)

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
            adapter = TodayListAdapter(lista, this)
            todayList.adapter = adapter
        }
    }

    private fun showPopupNewMenu(parentView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_new_objects, null)

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