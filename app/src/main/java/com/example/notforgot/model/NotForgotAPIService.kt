package com.example.notforgot.model

import com.example.notforgot.model.tasks.Category
import com.example.notforgot.model.tasks.Priority
import com.example.notforgot.model.tasks.Task
import com.example.notforgot.model.user.LoginResponse
import com.example.notforgot.model.user.RegisterResponse
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.DELETE


interface NotForgotAPIService {
    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("priorities")
    fun getPriorities(): Call<Array<Priority>>

    @FormUrlEncoded
    //@Headers("Content-type:application/json; charset=utf-8")
    @POST("categories")
    fun createCategory(
        //@Header("token") token: String
        @Field("name") name: String
    ): Call<Category>

    @GET("categories")
    fun getCategories(): Call<Array<Category>>

    @FormUrlEncoded
    @POST("tasks")
    fun createTask(
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("done") done: String,
        @Field("deadline") deadline: String,
        @Field("category_id") category_id: String,
        @Field("priority_id") priority_id: String
    ): Call<Task>

    @GET("tasks")
    fun getTasks(): Call<Array<Task>>

    @FormUrlEncoded
    @PATCH("tasks/{id}")
    fun updateTest(
        @Path("id") id: Int,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("done") done: String,
        @Field("deadline") deadline: String,
        @Field("category_id") category_id: String,
        @Field("priority_id") priority_id: String
    ): Call<Task>

    @DELETE("tasks/{id}")
    fun deleteGist(@Path("id") id: Int): Call<Task>
}