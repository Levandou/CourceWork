package com.velagissellint.presentation.containersDi

interface AppContainer {
    fun plusToDoListComponent(): ToDoListContainer

    fun plusAddItemComponent(): AddItemContainer
}
