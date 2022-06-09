package com.velagissellint.courcework.di.addItem

import androidx.lifecycle.ViewModel
import com.velagissellint.courcework.ViewModelKey
import com.velagissellint.courcework.di.scopes.AddItemScope
import com.velagissellint.presentation.addItem.AddItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AddItemModule {
    @AddItemScope
    @Binds
    @IntoMap
    @ViewModelKey(AddItemViewModel::class)
    fun bindViewModelFactory(addItemViewModel: AddItemViewModel): ViewModel
}