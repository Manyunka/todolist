package com.example.notforgot.view.tasks

import com.example.notforgot.model.tasks.Task

interface MainView {
    fun logout()
    fun dataExisted()
    fun loadData(tasks: List<Task>)
    fun enableSwipeToDeleteAndUndo()
}