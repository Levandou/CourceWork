package com.velagissellint.domain.useCases

import com.velagissellint.domain.models.Case
import javax.inject.Inject

class DeleteFromDbUseCase @Inject constructor(private val dbRepository: DbRepository) {
    fun deleteFromDb(case: Case){
        dbRepository.deleteFromDb(case)
    }
}