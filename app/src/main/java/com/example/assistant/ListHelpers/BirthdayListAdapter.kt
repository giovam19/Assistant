package com.example.assistant.ListHelpers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assistant.Model.Birthday
import com.example.assistant.Model.Task
import com.example.assistant.R
import com.example.assistant.SoloBirthdayActivity
import com.example.assistant.SoloTaskActivity

class BirthdayListAdapter(private val items: List<Birthday>, val context: Context) : RecyclerView.Adapter<BirthdayListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.itemName)
        val date: TextView = itemView.findViewById(R.id.itemDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_birthday, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.date.text = items[position].getShortDate()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SoloBirthdayActivity::class.java)
            intent.putExtra("uuid", items[position].id)
            context.startActivity(intent)
        }
    }
}