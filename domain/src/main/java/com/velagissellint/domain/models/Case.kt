package com.velagissellint.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_list")
data class Case(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val type: String,
    val description: String,
    val date: String
)