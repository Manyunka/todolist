package com.example.notforgot.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.notforgot.model.tasks.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    //@Query("SELECT * FROM task WHERE category_id = :categoryId")
    //fun getCategoryTasks(categoryId: Int): List<Task>

    @Insert
    fun addTask(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()
}
