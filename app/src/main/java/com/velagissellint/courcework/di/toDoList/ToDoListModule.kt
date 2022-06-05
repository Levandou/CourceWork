package com.velagissellint.courcework.di.toDoList

import androidx.lifecycle.ViewModel
import com.velagissellint.courcework.ViewModelKey
import com.velagissellint.courcework.di.scopes.ToDoListScope
import com.velagissellint.presentation.toDoList.ToDoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ToDoListModule {
    @ToDoListScope
    @Binds
    @IntoMap
    @ViewModelKey(ToDoListViewModel::class)
    fun bindViewModelFactory(toDoListViewModel: ToDoListViewModel): ViewModel
}