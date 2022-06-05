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
import com.velagissellint.domain.useCases.GetToDoListUseCase
import com.velagissellint.domain.useCases.paging.GetToDoPageUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
    private val addToDoItemUseCase:AddToDoItemUseCase,
    private val getToDoListUseCase: GetToDoListUseCase,
    private val getToDoPageUseCase: GetToDoPageUseCase
) : ViewModel(){
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val mutableToDoListLiveData=MutableLiveData<List<Case>>()
    val toDoListLiveData=mutableToDoListLiveData as LiveData<List<Case>>
    private val mutableToDoListPaging = MutableLiveData<PagingData<Case>>()
    val toDoListPaging =
        mutableToDoListPaging as LiveData<PagingData<Case>>

    private fun getListCaseFromDb(){
        mutableToDoListLiveData.value=getToDoListUseCase.getToDoList()
        Log.d("qwer",mutableToDoListLiveData.value.toString())
    }

    fun addItemToDb(case: Case){
        addToDoItemUseCase.addToDoItem(case)
    }

    fun loadToDoList() {
        getToDoPageUseCase.getToDoPage().cachedIn(viewModelScope)
            .take(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mutableToDoListPaging.value = it
            }, {
                Log.d("ERROR", it.message.toString())
            }).addTo(disposable)
    }

    init {
        loadToDoList()
        getListCaseFromDb()
    }
}