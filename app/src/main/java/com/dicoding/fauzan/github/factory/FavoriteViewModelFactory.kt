package com.dicoding.fauzan.github.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.fauzan.github.Injection
import com.dicoding.fauzan.github.room.UserRepository
import com.dicoding.fauzan.github.viewmodel.FavoriteViewModel

class FavoriteViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }
        return super.create(modelClass)
    }
    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null

        fun getInstance(context: Context): FavoriteViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.provideRepository(context))

            }
        }
    }
}