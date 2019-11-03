package com.example.notforgot.model.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(val id: Int, val name: String)