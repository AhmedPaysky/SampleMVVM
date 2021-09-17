package com.example.osama.ui.userdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.osama.data.entities.UserModel
import com.example.osama.data.repository.UserRepository
import com.example.osama.utils.Resource

class UserDetailViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _user = _id.switchMap { id ->
        repository.getUser(id)
    }
    val userModel: LiveData<Resource<UserModel>> = _user


    fun start(id: Int) {
        _id.value = id
    }

}
