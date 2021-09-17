package com.example.osama.data.repository

import com.example.osama.data.local.UserDao
import com.example.osama.data.remote.UserRemoteDataSource
import com.example.osama.utils.performGetOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserDao
) {

    fun getUser(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getUser(id) },
        networkCall = { remoteDataSource.getUser(id) },
        saveCallResult = { localDataSource.insert(it.data) }
    )

    fun getUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { remoteDataSource.getUsers() },
        saveCallResult = { localDataSource.insertAll(it.data) }
    )
}