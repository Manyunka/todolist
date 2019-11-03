package com.example.notforgot.view.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.notforgot.R
import com.example.notforgot.presenter.user.RegisterPresenter
import com.example.notforgot.view.tasks.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.emailEdit
import kotlinx.android.synthetic.main.activity_register.loginButton
import kotlinx.android.synthetic.main.activity_register.passwordEdit
import kotlinx.android.synthetic.main.activity_register.registerButton

class RegisterActivity : AppCompatActivity(), RegisterView {
    override fun showError(string: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(string)
            .setPositiveButton("OK") { _, _ -> }

        val alert = builder.create()
        alert.setTitle("Ошибка")
        alert.show()
    }

    override fun registerCompleted() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this, this)

        registerButton.setOnClickListener {
            val name = nameEdit.text.toString()
            val email = emailEdit.text.toString()
            val password = passwordEdit.text.toString()
            val confPassword = confirmPassword.text.toString()

            if (name.isEmpty()) {
                nameTil.error = "Введите имя"
                nameEdit.requestFocus()
                return@setOnClickListener
            } else nameTil.error = ""

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

            if (confPassword != password) {
                confirmPasswordTil.error = "Пароль не совпадает"
                confirmPassword.requestFocus()
                return@setOnClickListener
            } else confirmPasswordTil.error = ""

            presenter.onRegisterButtonClick(name, email, password)
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }

}
