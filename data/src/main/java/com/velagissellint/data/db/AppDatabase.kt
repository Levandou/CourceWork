package com.velagissellint.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.velagissellint.domain.models.Case

@Database(entities = [Case::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao():CaseDao

    companion object{
        const val DB_NAME="cases.db"
    }
}