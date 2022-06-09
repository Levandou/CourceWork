package com.velagissellint.courcework.di.addItem

import com.velagissellint.courcework.di.scopes.AddItemScope
import com.velagissellint.courcework.di.useCases.DbModule
import com.velagissellint.courcework.di.useCases.RepositoriesModule
import com.velagissellint.presentation.containersDi.AddItemContainer
import dagger.Subcomponent

@AddItemScope
@Subcomponent(modules = [AddItemModule::class, DbModule::class, RepositoriesModule::class])
interface AddItemComponent : AddItemContainer