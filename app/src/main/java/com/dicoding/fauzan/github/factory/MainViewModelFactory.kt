package com.dicoding.fauzan.github.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.fauzan.github.Injection
import com.dicoding.fauzan.github.room.UserRepository
import com.dicoding.fauzan.github.viewmodel.MainViewModel

class MainViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null

        fun getInstance(context: Context): MainViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.provideRepository(context))
            }
        }
    }
}