package com.dicoding.fauzan.github

import android.content.Context
import com.dicoding.fauzan.github.room.UserRepository
import com.dicoding.fauzan.github.room.UserRoomDatabase
import com.dicoding.fauzan.github.service.RetrofitConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userService = RetrofitConfig.getUserService()
        val database = UserRoomDatabase.getInstance(context)
        val userDao = database?.userDao()
        return UserRepository.getInstance(userService, userDao!!)
    }
}