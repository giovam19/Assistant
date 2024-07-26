package com.example.assistant.Model

import java.util.Date

class Birthday (
    id: String,
    var name: String,
    var date: String,
    isFavorite: Boolean
) : Reminder(id, isFavorite) {
    fun IsValidData(): String {
        if (name.isEmpty()) {
            return "Name can't be empty"
        }
        if (date.isEmpty()) {
            return "Date can't be empty"
        }

        return ""
    }
}