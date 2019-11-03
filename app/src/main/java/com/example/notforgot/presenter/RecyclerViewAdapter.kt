package com.example.notforgot.presenter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notforgot.R
import com.example.notforgot.model.tasks.Task


class RecyclerViewAdapter(
    private val tasks: MutableList<Task>
    ) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    open class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    class TaskViewHolder(v: View) : ViewHolder(v) {
        val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        val taskDescription: TextView = itemView.findViewById(R.id.taskDescript)
        val priorityColor: ImageView = itemView.findViewById(R.id.priorityColor)
        val checkDone: CheckBox = itemView.findViewById(R.id.checkDone)
    }

    class CategoryViewHolder(v: View) : ViewHolder(v) {
        val categoryTitle: TextView = itemView.findViewById(R.id.categoryTitle)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when(viewType) {
            0 -> {
                val item = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.task_item, parent, false)
                TaskViewHolder(item)
            }
            else -> {
                val item = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.category_item, parent, false)
                CategoryViewHolder(item)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is TaskViewHolder) {
            holder.taskTitle.text = tasks[position].title
            holder.taskDescription.text = tasks[position].description
            holder.priorityColor.setBackgroundColor(
                Color.parseColor(
                    tasks[position]
                        .priority
                        .color))
            holder.checkDone.isChecked = tasks[position].done != 0
        }

        if (holder is CategoryViewHolder) {
            holder.categoryTitle.text = tasks[position].title
        }
    }

    override fun getItemCount() = tasks.size

    override fun getItemViewType(position: Int): Int {
        return if (position % 3 == 0) 1 else 0
    }

    fun removeItem(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: Task, position: Int) {
        tasks.add(position, item)
        notifyItemInserted(position)
    }

    fun getData(): MutableList<Task> {
        return tasks
    }
}