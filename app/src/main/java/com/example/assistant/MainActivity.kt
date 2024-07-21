package com.example.assistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assistant.DataFactory.TestData
import com.example.assistant.ListHelpers.ListAdapter
import com.example.assistant.ListHelpers.ListItemDecorator

class MainActivity : AppCompatActivity() {
    lateinit var profileB: ImageView
    lateinit var tasksB: ConstraintLayout
    lateinit var brithsB: ConstraintLayout
    lateinit var notesB: ConstraintLayout
    lateinit var listsB: ConstraintLayout
    lateinit var favsB: ConstraintLayout
    lateinit var addB: ImageView

    lateinit var taksNum: TextView
    lateinit var brithsNum: TextView
    lateinit var notesNum: TextView
    lateinit var listsNum: TextView
    lateinit var favsNum: TextView

    lateinit var todayList: RecyclerView
    lateinit var itemDecoration : ListItemDecorator
    lateinit var adapter : ListAdapter
    lateinit var nothing: TextView

    var tasksNumI = 15
    var brithsNumI = 30
    var notesNumI = 10
    var listsNumI = 3
    var favsNumI = 8

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

    fun initButtons() {
        profileB = findViewById(R.id.profileButton)
        tasksB = findViewById(R.id.tasksContainer)
        brithsB = findViewById(R.id.birthsContainer)
        notesB = findViewById(R.id.notesContainer)
        listsB = findViewById(R.id.listsContainer)
        favsB = findViewById(R.id.favsContainer)
        addB = findViewById(R.id.addButton)
    }

    fun setButtonsListeners() {
        profileB.setOnClickListener {
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
        }
        tasksB.setOnClickListener {
            taksNum.text = (++tasksNumI).toString()
        }
        brithsB.setOnClickListener {
            brithsNum.text = (++brithsNumI).toString()
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
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()
        }
    }

    fun initNums() {
        taksNum  = findViewById(R.id.tasksNum)
        brithsNum = findViewById(R.id.birthsNum)
        notesNum = findViewById(R.id.notesNum)
        listsNum = findViewById(R.id.listsNum)
        favsNum = findViewById(R.id.favsNum)

        taksNum.text = tasksNumI.toString()
        brithsNum.text = brithsNumI.toString()
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

    fun setList() {
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
}