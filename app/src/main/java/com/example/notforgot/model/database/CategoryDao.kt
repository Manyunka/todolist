package com.example.notforgot.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.notforgot.model.tasks.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): List<Category>

    @Insert
    fun addCategory(category: Category)
}