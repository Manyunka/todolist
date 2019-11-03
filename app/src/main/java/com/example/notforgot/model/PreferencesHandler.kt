package com.example.notforgot.model

import android.content.Context

class PreferencesHandler(val context: Context) {

    fun saveToken(string: String) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putString(STRING_KEY, string)

        editor.apply()
    }

    fun readToken(): String {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(STRING_KEY, "") ?: ""

    }

    fun deleteToken() {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.remove(STRING_KEY)

        editor.apply()
    }

    companion object {
        val PREFERENCES_FILE_NAME = "MY_PREFERENCES"
        val STRING_KEY = "token_key"
    }
}
