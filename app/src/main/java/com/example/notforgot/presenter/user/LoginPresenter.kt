package com.example.notforgot.presenter.user

import android.content.Context
import com.example.notforgot.R
import com.example.notforgot.model.NetworkService
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.user.LoginResponse
import com.example.notforgot.view.user.LoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(var view: LoginView?, val context: Context) {

    fun onLoginButtonClick(
        email: String,
        password: String
    ) {
        val service = NetworkService(context).getWithoutHeader()
        val request = service.userLogin(email, password)

        request.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view?.showError(t.message.toString())
            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                val body = response.body()
                if (body != null) {
                    val preferencesHandler = PreferencesHandler(context)
                    preferencesHandler.saveToken(body.api_token)
                    view?.loginCompleted()
                } else {
                    if (response.code() == 404)
                        view?.showError(context.getString(R.string.error_404))
                    else view?.showError(response.code().toString() + ": " + response.message())
                }

            }

        })
    }

    fun onDestroyActivity() {
        view = null
    }
}