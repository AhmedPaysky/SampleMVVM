package com.example.osama.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey val id: Int,
    val first_name: String,
    val last_name: String,
    val avatar: String,
    val email: String
)