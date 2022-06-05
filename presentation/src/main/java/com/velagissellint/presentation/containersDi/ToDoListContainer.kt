package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.toDoList.ToDoListFragment

interface ToDoListContainer {
    fun inject(toDoListFragment: ToDoListFragment)
}
