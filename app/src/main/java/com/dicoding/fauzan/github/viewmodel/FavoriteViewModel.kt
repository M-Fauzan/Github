package com.dicoding.fauzan.github.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.fauzan.github.User
import com.dicoding.fauzan.github.room.UserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }
    fun getFavoriteUsers() = userRepository.getFavoriteUsers()
}