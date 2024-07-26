package com.example.assistant.Model

import java.util.Date

class Note (
    var id: String,
    var title: String,
    var note: String,
    var creationDate: Date,
    var isFavorite: Boolean
) {
    fun IsValidData(): String {
        if (title.isEmpty()) {
            return "Title can't be empty"
        }
        if (note.isEmpty()) {
            return "Note can't be empty"
        }

        return ""
    }
}