package com.velagissellint.courcework

import android.app.Application
import com.velagissellint.courcework.di.app.AppComponent
import com.velagissellint.courcework.di.app.DaggerAppComponent
import com.velagissellint.presentation.containersDi.AppContainer
import com.velagissellint.presentation.containersDi.ContainerAppContainer

lateinit var applicationComponent: AppComponent

class App:Application(), ContainerAppContainer {
    override fun onCreate() {
        super.onCreate()
        applicationComponent= DaggerAppComponent.builder().application(this).build()
    }

    override fun appContainer(): AppContainer = applicationComponent
}