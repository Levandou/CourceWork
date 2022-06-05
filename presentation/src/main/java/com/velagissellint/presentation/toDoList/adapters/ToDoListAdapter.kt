package com.velagissellint.presentation.toDoList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.velagissellint.domain.models.Case
import com.velagissellint.presentation.R
import com.velagissellint.presentation.toDoList.ToDoListDiffCallback
import com.velagissellint.presentation.toDoList.ToDoListViewHolder

class ToDoListAdapter() : PagingDataAdapter<Case, ToDoListViewHolder>(
    ToDoListDiffCallback()
) {
    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.typeOfToDo.text = type
            holder.toDo.text = description
            holder.date.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_of_to_do,
            parent,
            false
        )
        return ToDoListViewHolder(view)
    }
}