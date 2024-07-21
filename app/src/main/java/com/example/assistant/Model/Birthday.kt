package com.example.assistant.Model

import java.util.Date

class Birthday (
    id: String,
    var name: String,
    var date: Date,
    isFavorite: Boolean
) : Reminder(id, isFavorite) {
}