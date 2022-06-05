package com.velagissellint.domain.useCases.paging

import androidx.paging.PagingData
import com.velagissellint.domain.models.Case
import io.reactivex.rxjava3.core.Flowable

interface GetToDoPageRepository {
    fun getToDoPage(): Flowable<PagingData<Case>>
}