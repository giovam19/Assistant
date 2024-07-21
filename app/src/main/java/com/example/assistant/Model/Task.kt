package com.example.assistant.Model

import java.util.Date

class Task(
    id: String,
    var title: String,
    var description: String,
    var date: Date,
    var hour: String,
    isFavorite: Boolean
) : Reminder(id, isFavorite) {
}