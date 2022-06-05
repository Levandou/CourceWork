package com.velagissellint.domain.useCases

import com.velagissellint.domain.models.Case

interface DbRepository {
    fun addToDoItem(case: Case)

    fun getToDoList():List<Case>
}