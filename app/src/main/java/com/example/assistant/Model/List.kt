package com.example.assistant.Model

import java.util.Date
import kotlin.collections.List

class List (
    var id: String,
    var title: String,
    var elements: List<ListItem>,
    var isFavorite: Boolean
) {}