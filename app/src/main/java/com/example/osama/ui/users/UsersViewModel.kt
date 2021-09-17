package com.example.osama.ui.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.osama.data.repository.UserRepository

class UsersViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val users = repository.getUsers()
}
