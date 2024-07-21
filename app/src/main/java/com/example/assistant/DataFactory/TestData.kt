package com.example.assistant.DataFactory

import com.example.assistant.Model.Birthday
import com.example.assistant.Model.Reminder
import com.example.assistant.Model.Task
import java.util.Date

class TestData {
    fun todayItems(): List<Reminder> {
        val list = mutableListOf<Reminder>()

        val a = Task("1", "Dentista", "Llevar tarjeta sanitaria", Date(), "16:15", false)
        val b = Birthday("1", "Alejandro", Date(), false)
        val c = Birthday("1", "Ricardo", Date(), false)
        val d = Task("1", "Ir al Banco", "", Date(), "08:30", false)
        val e = Task("1", "Comprar fruta", "", Date(), "10:21", false)
        val f = Task("1", "Lavar ropa", "", Date(), "22:45", false)

        list.add(a)
        list.add(b)
        list.add(c)
        list.add(d)
        list.add(e)
        list.add(f)

        return list
    }
}