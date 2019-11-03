package com.example.notforgot.model.user

data class RegisterResponse(val email: String,
                            val name: String,
                            val id: Int,
                            val api_token: String)