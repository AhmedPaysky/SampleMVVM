package com.example.osama.data.remote

import com.example.osama.data.entities.SingleUser
import com.example.osama.data.entities.UserList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users/")
    suspend fun getAllUsers(@Query("per_page") perPage: String = "20"): Response<UserList>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<SingleUser>
}