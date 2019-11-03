package com.example.notforgot.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notforgot.model.tasks.Task

@Database(entities = arrayOf(Task::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    //abstract fun categoryDao(): CategoryDao
    //abstract fun priorityDao(): PriorityDao
}
