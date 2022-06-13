package com.velagissellint.domain.useCases

import com.velagissellint.domain.models.Case

interface DbRepository {
    fun addToDoItem(case: Case)

    fun getToDoList(stringFilter: String):List<Case>

    fun deleteFromDb(case: Case)
}