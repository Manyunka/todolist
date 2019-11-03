package com.example.notforgot.presenter.tasks

import android.content.Context
import com.example.notforgot.view.tasks.CreateTaskView

class CreateTaskPresenter(var view: CreateTaskView?, val context: Context) {
    fun onDataEditClick() {
        view?.getDate()
    }



    fun onDestroyActivity() {
        view = null
    }
}