package com.velagissellint.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.velagissellint.domain.models.Case
import io.reactivex.rxjava3.core.Single

@Dao
interface CaseDao {
    @Query("SELECT * FROM to_do_list ORDER BY id LIMIT :limit OFFSET :offset")
    fun getToDoList(limit: Int, offset: Int): List<Case>

    @Query("SELECT * FROM to_do_list ORDER BY id ")
    fun getToDoListSingle(): Single<List<Case>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToDoList(case: Case)
}