package com.example.notforgot.model

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService(val context: Context) {
    private val preferencesHandler = PreferencesHandler(context)
    private val token = preferencesHandler.readToken()
    private lateinit var retrofit: Retrofit

    val client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        chain.proceed(newRequest)
    }.build()

    fun getWithHeader(): NotForgotAPIService {
        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://practice.mobile.kreosoft.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NotForgotAPIService::class.java)
    }

    fun getWithoutHeader(): NotForgotAPIService {
        retrofit = Retrofit.Builder()
            .baseUrl("http://practice.mobile.kreosoft.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NotForgotAPIService::class.java)
    }
}