package com.velagissellint.presentation.toDoList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.presentation.R

class ToDoListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val typeOfToDo: TextView = view.findViewById(R.id.tv_type_of_to_do)
    val toDo: TextView = view.findViewById(R.id.tv_to_do)
    val date: TextView = view.findViewById(R.id.tv_date)
}