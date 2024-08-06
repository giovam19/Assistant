package com.example.assistant.DataFactory

import com.example.assistant.Model.Birthday
import com.example.assistant.Model.Note
import com.example.assistant.Model.Reminder
import com.example.assistant.Model.Task
import com.example.assistant.Model.List as OList
import java.util.Date
import java.util.UUID

class TestData {
    companion object {
        var tasks: MutableList<Task> = mutableListOf()
        var births: MutableList<Birthday> = mutableListOf()
        var notes: MutableList<Note> = mutableListOf()
        var lists: MutableList<OList> = mutableListOf()
    }

    fun todayItems(): List<Reminder> {
        val list = mutableListOf<Reminder>()

        list.addAll(tasks)
        list.addAll(births)

        return list
    }

    fun fillTasks() {
        if (tasks.isNotEmpty())
            return

        val a = Task(UUID.randomUUID().toString(), "Dentista", "Llevar tarjeta sanitaria", "27/06/2024", "16:15", false)
        val d = Task(UUID.randomUUID().toString(), "Ir al Banco", "", "22/12/2024", "08:30", false)
        val e = Task(UUID.randomUUID().toString(), "Comprar fruta", "", "14/10/2024", "10:21", false)
        val f = Task(UUID.randomUUID().toString(), "Lavar ropa", "", "05/02/2024", "22:45", false)

        tasks.add(a)
        tasks.add(d)
        tasks.add(e)
        tasks.add(f)
    }

    fun fillBirthdays() {
        if (births.isNotEmpty())
            return

        val b = Birthday(UUID.randomUUID().toString(), "Alejandro", "27/06/2024", false)
        val c = Birthday(UUID.randomUUID().toString(), "Ricardo", "05/08/2024", false)

        births.add(b)
        births.add(c)
    }
}