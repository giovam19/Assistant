package com.example.assistant.Model

import java.util.Date
import kotlin.collections.List

class List (
    var id: String,
    var title: String,
    var elements: List<ListItem>,
    var creationDate: Date,
    var isFavorite: Boolean
) {
    fun IsValidData(): String {
        if (title.isEmpty()) {
            return "Title can't be empty"
        }
        if (elements.isEmpty()) {
            return "List can't be empty"
        }

        return ""
    }
}