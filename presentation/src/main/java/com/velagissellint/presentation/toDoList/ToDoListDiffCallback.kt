package com.velagissellint.presentation.toDoList

import androidx.recyclerview.widget.DiffUtil
import com.velagissellint.domain.models.Case

class ToDoListDiffCallback : DiffUtil.ItemCallback<Case>() {
    override fun areItemsTheSame(oldItem: Case, newItem: Case) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Case, newItem: Case) = oldItem == newItem
}