package com.example.notforgot.model.tasks

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class Task(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val done: Int,
    @Embedded(prefix = "category_") val category: Category,
    @Embedded(prefix = "priority_") val priority: Priority,
    val deadline: Int,
    val created: Int
)