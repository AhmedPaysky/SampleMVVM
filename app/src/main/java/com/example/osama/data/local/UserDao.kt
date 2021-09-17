package com.example.osama.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.osama.data.entities.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<UserModel>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): LiveData<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userModels: List<UserModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userModel: UserModel)


}