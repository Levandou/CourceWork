package com.velagissellint.courcework.di.useCases

import android.app.Application
import androidx.room.Room
import com.velagissellint.data.db.AppDatabase
import com.velagissellint.data.db.CaseDao
import dagger.Module
import dagger.Provides

@Module
class DbModule {
    @Provides
    fun getRoomDbInstance(application: Application): AppDatabase =
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).allowMainThreadQueries().build()

    @Provides
    fun getDao(appDatabase: AppDatabase): CaseDao =
        appDatabase.resultDao()
}
