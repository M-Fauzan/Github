package com.dicoding.fauzan.github.viewmodel

import androidx.lifecycle.*
import com.dicoding.fauzan.github.User
import com.dicoding.fauzan.github.room.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun search(username: String) = userRepository.search(username)


    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

}