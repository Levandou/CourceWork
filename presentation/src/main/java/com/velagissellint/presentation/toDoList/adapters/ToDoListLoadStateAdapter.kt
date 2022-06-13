package com.velagissellint.presentation.toDoList.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.velagissellint.presentation.R

class ToDoListLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ToDoListLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadStateViewHolder(parent, retry)

    class LoadStateViewHolder(
        parent: ViewGroup,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.load_state, parent, false)
    ) {
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
        private val errorMsg = itemView.findViewById<TextView>(R.id.messageTextView)
        private val retry = itemView.findViewById<Button>(R.id.tryAgainButton)
            .also { it.setOnClickListener { retry.invoke() } }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.visibility = toVisibility(loadState is LoadState.Loading)
            retry.visibility = toVisibility(loadState !is LoadState.Loading)
            errorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
        }

        private fun toVisibility(constraint: Boolean): Int = if (constraint) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}