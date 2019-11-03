package com.example.notforgot.view.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.notforgot.R
import com.example.notforgot.presenter.user.LoginPresenter
import com.example.notforgot.view.tasks.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    override fun showError(string: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(string)
            .setPositiveButton("OK") { _, _ -> }

        val alert = builder.create()
        alert.setTitle("Ошибка")
        alert.show()
    }

    override fun loginCompleted() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this, applicationContext)

        loginButton.setOnClickListener {

            val email = emailEdit.text.toString()
            val password = passwordEdit.text.toString()

            if (email.isEmpty()) {
                emailTil.error = "Введите e-mail"
                emailEdit.requestFocus()
                return@setOnClickListener
            } else emailTil.error = ""

            if (password.isEmpty()) {
                passwordTil.error = "Введите пароль"
                passwordEdit.requestFocus()
                return@setOnClickListener
            } else passwordTil.error = ""

            presenter.onLoginButtonClick(email, password)
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }
}
