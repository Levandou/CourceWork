package com.velagissellint.courcework.di.app

import android.app.Application
import com.velagissellint.courcework.App
import com.velagissellint.courcework.di.toDoList.ToDoListComponent
import com.velagissellint.presentation.containersDi.AppContainer
import com.velagissellint.presentation.containersDi.ToDoListContainer
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AppContainer {
    override fun plusToDoListComponent(): ToDoListComponent

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}