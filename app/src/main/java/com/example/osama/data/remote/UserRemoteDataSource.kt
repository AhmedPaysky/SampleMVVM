package com.example.osama.data.remote

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
): BaseDataSource() {

    suspend fun getUsers() = getResult { userService.getAllUsers() }
    suspend fun getUser(id: Int) = getResult { userService.getUser(id) }
}