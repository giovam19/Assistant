package com.example.assistant.Controller

import android.content.Context
import android.text.InputType
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.assistant.R

class ItemListController(private val context: Context, view: View, container: LinearLayout, list: MutableList<ItemListController>) {
    private val checkbox: ImageView = view.findViewById(R.id.listItemCheckbox)
    private val text: EditText = view.findViewById(R.id.listItemText)
    private val delete: ImageView = view.findViewById(R.id.listItemDelete)
    private var checked: Boolean = false

    init {
        checkbox.setOnClickListener {
            checked = !checked
            if (checked) {
                checkbox.setImageResource(R.drawable.checked)

                applyOrRemoveStrikethrough(text.text.toString(), text)
                text.isFocusable = false
                text.isFocusableInTouchMode = false
                text.isClickable = false
                text.isLongClickable = false
                text.inputType = InputType.TYPE_NULL
                text.setTextColor(context.getColor(R.color.black_50))
            } else {
                checkbox.setImageResource(R.drawable.unchecked)

                applyOrRemoveStrikethrough(text.text.toString(), text)
                text.isFocusable = true
                text.isFocusableInTouchMode = true
                text.isClickable = true
                text.isLongClickable = true
                text.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                text.setTextColor(context.getColor(R.color.black))
            }
        }
        delete.setOnClickListener {
            container.removeView(view)
            list.remove(this)
        }
    }

    private fun applyOrRemoveStrikethrough(text: String, editText: EditText) {
        val spannableString = SpannableString(text)
        if (checked) {
            spannableString.setSpan(StrikethroughSpan(), 0, text.length, 0)
        }
        editText.setText(spannableString)
        //editText.setSelection(text.length) // Mueve el cursor al final del texto
    }

    fun getChecked() : Boolean {
        return checked
    }

    fun getText() : String {
        return text.text.toString()
    }
}