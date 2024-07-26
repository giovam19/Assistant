package com.example.assistant.Model

import java.util.Date

class Note (
    var id: String,
    var title: String,
    var note: String,
    var creationDate: Date,
    var isFavorite: Boolean
) {
}