package com.velagissellint.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.velagissellint.domain.models.Case
import io.reactivex.rxjava3.core.Single

@Dao
interface CaseDao {
    @Query("SELECT * FROM to_do_list WHERE date == :stringFilter ORDER BY id LIMIT :limit OFFSET :offset")
    fun getPartsToDoList(limit: Int, offset: Int, stringFilter: String): List<Case>

    @Query("SELECT * FROM to_do_list WHERE date == :stringFilter ORDER BY id")
    fun getToDoList(stringFilter: String): List<Case>

    @Query("SELECT * FROM to_do_list ORDER BY id ")
    fun getToDoListSingle(): Single<List<Case>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToDoList(case: Case)

    @Delete
    fun deleteToDoItemFromDb(case: Case)
}