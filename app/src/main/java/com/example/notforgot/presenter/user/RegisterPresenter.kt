package com.example.notforgot.presenter.user

import androidx.appcompat.app.AppCompatActivity
import com.example.notforgot.model.NetworkService
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.user.RegisterResponse
import com.example.notforgot.view.user.RegisterView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterPresenter(var view: RegisterView?, val context: AppCompatActivity) {

    fun onRegisterButtonClick(
        name: String,
        email: String,
        password: String
    ) {

        val service = NetworkService(context).getWithoutHeader()
        val request = service.userRegister(email, name, password)

        request.enqueue(object : Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                view?.showError(t.message.toString())
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {

                val body = response.body()
                if (body != null) {
                    val preferencesHandler = PreferencesHandler(context)
                    preferencesHandler.saveToken(body.api_token)
                    view?.registerCompleted()
                } else {
                    view?.showError(response.code().toString() + ": " + response.message())
                }

            }

        })

    }

    fun onDestroyActivity() {
        view = null
    }
}