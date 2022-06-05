package com.velagissellint.domain.useCases

import com.velagissellint.domain.models.Case
import javax.inject.Inject

class AddToDoItemUseCase @Inject constructor(private val dbRepository: DbRepository){
    fun addToDoItem(case:Case){
        dbRepository.addToDoItem(case)
    }
}