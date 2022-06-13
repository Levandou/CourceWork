package com.velagissellint.domain.useCases

import javax.inject.Inject

class GetToDoListUseCase @Inject constructor(private val dbRepository: DbRepository) {
    fun getToDoList(stringFilter: String) =
        dbRepository.getToDoList(stringFilter)
}