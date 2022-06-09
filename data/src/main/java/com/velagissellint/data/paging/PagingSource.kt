package com.velagissellint.data.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.velagissellint.data.db.CaseDao
import com.velagissellint.domain.models.Case
import io.reactivex.rxjava3.core.Single

class PagingSource(
    private val caseDao: CaseDao,
    filterString: String
) : RxPagingSource<Int, Case>() {
    override fun getRefreshKey(state: PagingState<Int, Case>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)
        return if (page == null)
            null
        else
            page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Case>> {
        val position = params.key ?: 1
        val pageSize = params.loadSize

        return Single.fromCallable {
            caseDao.getToDoList(
                limit = pageSize,
                offset = pageSize * (position - 1)
            )
        }
            .map {
                LoadResult.Page(
                    data = it,
                    nextKey = if (pageSize > it.size) null else position + 1,
                    prevKey = if (position == 1) null else position - 1
                ) as LoadResult<Int, Case>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

}