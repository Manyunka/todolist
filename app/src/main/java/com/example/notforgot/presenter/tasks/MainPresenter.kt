package com.example.notforgot.presenter.tasks

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.notforgot.model.NetworkService
import com.example.notforgot.model.Repository
import com.example.notforgot.model.tasks.Task
import com.example.notforgot.view.tasks.MainView
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var view: MainView?, val context: Context) {

    fun loadDataBase() {
        val service = NetworkService(context).getWithHeader()
        val request = service.getTasks()

        request.enqueue(object : Callback<Array<Task>> {
            override fun onFailure(call: Call<Array<Task>>, t: Throwable) {
                showError(t.message.toString())
            }
            override fun onResponse(call: Call<Array<Task>>, response: Response<Array<Task>>) {
                val body = response.body()
                if (body != null) {
                    val repository = Repository(context)
                    if (body.isNotEmpty()) {
                        val tasks: Array<Task> = body
                        //repository.deleteAllTasks {  }
                        for (item in tasks)
                            repository.saveNewTask(item) { }
                        repository.getAllTasks {
                            view?.dataExisted()
                            view?.loadData(it)
                        }
                    }
                } else {
                    showError(response.code().toString() + ": " + response.message())
                }

            }

        })
    }

    fun showError(string: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(string)
            .setPositiveButton("OK") { _, _ -> }

        val alert = builder.create()
        alert.setTitle("Ошибка")
        alert.show()
    }

    fun internetCheck(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

    fun onDestroyActivity() {
        view = null
    }
}