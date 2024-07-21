package com.example.assistant.ListHelpers

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assistant.Model.Birthday
import com.example.assistant.Model.Reminder
import com.example.assistant.Model.Task
import com.example.assistant.R


class ListAdapter(private val items: List<Reminder>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.itemImage)
        val name: TextView = itemView.findViewById(R.id.itemName)
        val hour: TextView = itemView.findViewById(R.id.itemHour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.today_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if (item is Task) {
            holder.icon.setImageResource(R.drawable.task_icon2)
            holder.name.text = item.title
            holder.hour.text = item.hour
        } else if (item is Birthday) {
            holder.icon.setImageResource(R.drawable.birthday_ic1)
            holder.name.text = "Felicita a ${item.name}"
            holder.hour.visibility = TextView.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}