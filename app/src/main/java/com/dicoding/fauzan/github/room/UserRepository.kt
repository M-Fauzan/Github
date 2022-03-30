package com.dicoding.fauzan.github.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.fauzan.github.BuildConfig
import com.dicoding.fauzan.github.Result
import com.dicoding.fauzan.github.User
import com.dicoding.fauzan.github.service.UserService

class UserRepository(private val userService: UserService, private val userDao: UserDao) {

    fun search(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val searchResponse =
                userService.search(BuildConfig.TOKEN_KEY, username)
            val searchItems = searchResponse.items

            val itemResponse = searchItems.map {
                userService.examine(BuildConfig.TOKEN_KEY, it.login)
            }

            val noInput = ""

            emitSource(MutableLiveData(Result.Success(itemResponse.map {
                User(
                    it.login, it.name ?: noInput, it.avatarUrl, it.location ?: noInput,
                    it.publicRepos, it.company ?: noInput,
                    it.followers, it.following, userDao.isFavorite(it.login)
                )
            })))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }

    }
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
        user.isFavorite = true
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        user.isFavorite = false
        userDao.updateUser(user)
        userDao.deleteUser(user)
    }

    fun getFavoriteUsers(): LiveData<List<User>> {
        return userDao.getFavoriteUsers()
    }

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(userService: UserService, userDao: UserDao): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(userService, userDao).also {
                    instance = it
                }
            }
        }
    }


}