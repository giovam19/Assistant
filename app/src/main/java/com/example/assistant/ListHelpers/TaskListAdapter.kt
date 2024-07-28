package com.example.assistant.ListHelpers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.assistant.Model.Task
import androidx.recyclerview.widget.RecyclerView
import com.example.assistant.ListTasksActivity
import com.example.assistant.R
import com.example.assistant.SoloTaskActivity

class TaskListAdapter(private val items: List<Task>, val context: Context) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.itemName)
        val date: TextView = itemView.findViewById(R.id.itemDate)
        val hour: TextView = itemView.findViewById(R.id.itemHour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].title
        holder.date.text = items[position].getShortDate()
        holder.hour.text = items[position].hour

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SoloTaskActivity::class.java)
            intent.putExtra("uuid", items[position].id)
            context.startActivity(intent)
        }
    }
}