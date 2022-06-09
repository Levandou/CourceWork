package com.velagissellint.presentation.addItem

import androidx.lifecycle.ViewModel
import com.velagissellint.domain.models.Case
import com.velagissellint.domain.useCases.AddToDoItemUseCase
import javax.inject.Inject

class AddItemViewModel @Inject constructor(private val addToDoItemUseCase: AddToDoItemUseCase) :
    ViewModel() {
        fun addToDb(case: Case){
            addToDoItemUseCase.addToDoItem(case)
        }
}