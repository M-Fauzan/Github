package com.dicoding.fauzan.github.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.fauzan.github.BuildConfig
import com.dicoding.fauzan.github.data.FollowerResponseItem
import com.dicoding.fauzan.github.service.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/*
    Follower and Following ViewModel
 */
class FollowerViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _isLoading

    private val _followerList = MutableLiveData<List<FollowerResponseItem>>()
    val followerList: LiveData<List<FollowerResponseItem>> = _followerList

    private val _followingList = MutableLiveData<List<FollowerResponseItem>>()
    val followingList: LiveData<List<FollowerResponseItem>> = _followingList

    fun listFollowers(username: String) {
        _isLoading.value = true
        val client = RetrofitConfig.getUserService().listFollowers(BuildConfig.TOKEN_KEY, username)

        client.enqueue(object : Callback<List<FollowerResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response<List<FollowerResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _followerList.value = response.body()

                }
                else {
                    Log.e("listFollowers failed: ", response.message())
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                Log.e("listFollowers failed:", t.message!!)
                _isLoading.value = false
            }
        })
    }
    fun listFollowing(username: String) {
        _isLoading.value = true
        val client = RetrofitConfig.getUserService().listFollowing(BuildConfig.TOKEN_KEY,username)
        client.enqueue(object : Callback<List<FollowerResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response<List<FollowerResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _followingList.value = response.body()
                }
                else {
                    Log.e("listFollowing failed: ", response.message())
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                Log.e("listFollowing failed:", t.message!!)
                _isLoading.value = false
            }
        })
    }
}