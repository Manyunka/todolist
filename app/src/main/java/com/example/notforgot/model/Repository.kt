package com.example.notforgot.model

import android.content.Context
import androidx.room.Room
import com.example.notforgot.model.database.AppDatabase
import com.example.notforgot.model.tasks.Category
import com.example.notforgot.model.tasks.Priority
import com.example.notforgot.model.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(val context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, TASKS_DATABASE
    ).build()

    fun getAllTasks(onGetTask: (tasks: List<Task>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val tasks = withContext(Dispatchers.IO) {
                db.taskDao().getAllTasks()
            }
            onGetTask(tasks)
        }
    }

    fun saveNewTask(newTask: Task, onSave: ()-> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            db.taskDao().addTask(newTask)
            onSave()
        }
    }


    fun deleteAllTasks(onDelete: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            db.taskDao().deleteAllTasks()
        }
        onDelete()
    }

    companion object {
        private val TASKS_DATABASE = "tasksDatabase"
    }
}

/*fun getCategoryTasks(categoryId: Int, onGetTask: (tasks: List<Task>) -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        val tasks = withContext(Dispatchers.IO) {
            db.taskDao().getCategoryTasks(categoryId) }
        onGetTask(tasks)
    }
}*/

/*fun getAllCategories(onGetCategory: (categories: List<Category>) -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        val categories = withContext(Dispatchers.IO) {
            db.categoryDao().getAllCategories() }
        onGetCategory(categories)
    }
}

fun saveNewCategory(newCategory: Category, onSaveCategory: () -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        db.categoryDao().addCategory(newCategory)
        onSaveCategory()
    }
}

fun getAllPriorities(onGetPriority: (priorities: List<Priority>) -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        val priorities = withContext(Dispatchers.IO) {
            db.priorityDao().getAllPriorities() }
        onGetPriority(priorities)
    }
}*/
