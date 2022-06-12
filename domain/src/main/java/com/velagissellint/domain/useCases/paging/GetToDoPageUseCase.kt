package com.velagissellint.domain.useCases.paging

import javax.inject.Inject

class GetToDoPageUseCase @Inject constructor(
    private val getToDoPageRepository: GetToDoPageRepository
) {
    fun getToDoPage(date: String) = getToDoPageRepository.getToDoPage(date)
}