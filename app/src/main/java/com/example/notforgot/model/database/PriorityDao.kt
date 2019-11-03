package com.example.notforgot.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.notforgot.model.tasks.Priority

@Dao
interface PriorityDao {
    @Query("SELECT * FROM priorities")
    fun getAllPriorities(): List<Priority>
}