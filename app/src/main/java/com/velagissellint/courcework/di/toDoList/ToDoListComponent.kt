package com.velagissellint.courcework.di.toDoList

import com.velagissellint.courcework.di.scopes.ToDoListScope
import com.velagissellint.courcework.di.useCases.DbModule
import com.velagissellint.courcework.di.useCases.RepositoriesModule
import com.velagissellint.presentation.containersDi.ToDoListContainer
import dagger.Subcomponent

@ToDoListScope
@Subcomponent(modules = [ToDoListModule::class, DbModule::class, RepositoriesModule::class])
interface ToDoListComponent : ToDoListContainer