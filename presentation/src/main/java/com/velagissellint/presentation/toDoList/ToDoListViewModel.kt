package com.velagissellint.presentation.toDoList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.velagissellint.domain.models.Case
import com.velagissellint.domain.useCases.AddToDoItemUseCase
import com.velagissellint.domain.useCases.DeleteFromDbUseCase
import com.velagissellint.domain.useCases.GetToDoListUseCase
import com.velagissellint.domain.useCases.paging.GetToDoPageUseCase
import com.velagissellint.presentation.convertDateForUser
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.*
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
    private val addToDoItemUseCase: AddToDoItemUseCase,
    private val getToDoListUseCase: GetToDoListUseCase,
    private val getToDoPageUseCase: GetToDoPageUseCase,
    private val deleteFromDbUseCase: DeleteFromDbUseCase
) : ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val mutableToDoListPaging = MutableLiveData<PagingData<Case>>()
    lateinit var toDoList: List<Case>
    val toDoListPaging =
        mutableToDoListPaging as LiveData<PagingData<Case>>

    private fun getListFromDb(stringFilter: String) {
        toDoList = getToDoListUseCase.getToDoList(stringFilter)
        Log.d("zxcvb", toDoList.toString())
    }

    fun addItemToDb(case: Case) {
        addToDoItemUseCase.addToDoItem(case)
    }

    fun loadToDoList(date: String) {
        getListFromDb(date)
        getToDoPageUseCase.getToDoPage(date).cachedIn(viewModelScope)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableToDoListPaging.value = it
            }, {
                Log.d("ERROR", it.message.toString())
            }).addTo(disposable)
    }

    fun deleteFromDb(case: Case){
        deleteFromDbUseCase.deleteFromDb(case)
    }

    init {
        Calendar.getInstance().apply {
            loadToDoList(
                convertDateForUser(
                    get(Calendar.DAY_OF_MONTH),
                    get(Calendar.MONTH),
                    get(Calendar.YEAR)
                )
            )
        }
    }
}