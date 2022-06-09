package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.addItem.AddItemFragment

interface AddItemContainer {
    fun inject(addItemFragment: AddItemFragment)
}