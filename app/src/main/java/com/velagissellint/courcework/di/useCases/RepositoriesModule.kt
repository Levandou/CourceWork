package com.velagissellint.courcework.di.useCases

import com.velagissellint.data.RepositoryImpl
import com.velagissellint.data.db.CaseDao
import com.velagissellint.data.paging.PagingSource
import com.velagissellint.domain.useCases.DbRepository
import com.velagissellint.domain.useCases.paging.GetToDoPageRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {
    @Provides
    fun provideDbRepository(
        caseDao: CaseDao,
        //pagingSource: PagingSource
    ): DbRepository =
        RepositoryImpl(
            caseDao
            //    , pagingSource
        )

    @Provides
    fun providePagingRepository(
        caseDao: CaseDao,
       // pagingSource: PagingSource
    ): GetToDoPageRepository =
        RepositoryImpl(
            caseDao
            //, pagingSource
        )
}
