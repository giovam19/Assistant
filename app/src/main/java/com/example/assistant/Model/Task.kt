package com.example.assistant.Model

import java.util.Date

class Task(
    id: String,
    var title: String,
    var description: String,
    var date: String,
    var hour: String,
    isFavorite: Boolean
) : Reminder(id, isFavorite) {
    fun IsValidData(): String {
        if (title.isEmpty()) {
            return "Title can't be empty"
        }
        if (date.isEmpty()) {
            return "Date can't be empty"
        }
        if (hour.isEmpty()) {
            return "Hour can't be empty"
        }

        return ""
    }

    fun getShortDate(): String {
        val pieces = date.split('/')

        return "${pieces[0]}/${pieces[1]}"
    }
}