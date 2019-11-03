package com.example.notforgot.model.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "priorities")
data class Priority(
    val id: Int,
    val name: String,
    val color: String
)