package com.example.notforgot.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(val email: String,
                val name: String,
                @PrimaryKey val id: Int = 0,
                val api_token: String)
