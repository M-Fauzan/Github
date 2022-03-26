package com.dicoding.fauzan.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.dicoding.fauzan.github.service.RetrofitConfig

class UserRepository {
    private lateinit var userDao: UserDao

    private lateinit var retrofitConfig: RetrofitConfig

    fun search(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val searchResponse =
                RetrofitConfig.getUserService().search(BuildConfig.TOKEN_KEY, username)
            val searchItems = searchResponse.items

            val itemResponse = searchItems.map {
                RetrofitConfig.getUserService().examine(BuildConfig.TOKEN_KEY, it.login)
            }

            val noInput = ""

            emitSource(MutableLiveData(Result.Success(itemResponse.map {
                User(
                    it.login, it.name ?: noInput, it.avatarUrl, it.location ?: noInput,
                    it.publicRepos, it.company ?: noInput,
                    it.followers, it.following
                )
            })))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }


    }
    fun examine(username: String) {

    }
    companion object {
        private var instance: UserRepository? = null
        fun getInstance(): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository().also {
                    instance = it
                }
            }
        }
    }


}