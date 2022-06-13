package com.velagissellint.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.flowable
import com.velagissellint.data.db.CaseDao
import com.velagissellint.data.paging.PagingSource
import com.velagissellint.domain.models.Case
import com.velagissellint.domain.useCases.DbRepository
import com.velagissellint.domain.useCases.paging.GetToDoPageRepository

class RepositoryImpl(
    private val caseDao: CaseDao
    //private val pagingSource: PagingSource
) : DbRepository, GetToDoPageRepository {
    override fun addToDoItem(case: Case) {
        caseDao.addToDoList(case)
    }

    override fun getToDoList(stringFilter: String): List<Case> =
        caseDao.getToDoList(stringFilter)

    override fun deleteFromDb(case: Case) {
        caseDao.deleteToDoItemFromDb(case)
    }

    override fun getToDoPage(date: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false,
            initialLoadSize = 20,
            maxSize = 100
        ),
        pagingSourceFactory = { PagingSource(caseDao, date) }
    ).flowable
}