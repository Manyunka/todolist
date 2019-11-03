package com.example.notforgot.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.view.tasks.MainActivity
import com.example.notforgot.view.user.LoginActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferencesHandler = PreferencesHandler(applicationContext)
        val intent: Intent

        intent = if(preferencesHandler.readToken().isEmpty())
            Intent(this, LoginActivity::class.java)
        else Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }
}
