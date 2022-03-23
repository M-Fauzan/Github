package com.dicoding.fauzan.github.viewmodel

import android.util.Log
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.fauzan.github.data.ItemsItem
import com.dicoding.fauzan.github.data.UserSearchResponse
import com.dicoding.fauzan.github.datastore.Settings
import com.dicoding.fauzan.github.service.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val settings: Settings) : ViewModel() {
    private var _userList = MutableLiveData<List<ItemsItem>>()
    val userList : LiveData<List<ItemsItem>> = _userList

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun search(username: String) {
        _isLoading.value = true
        val client = RetrofitConfig.getUserService().search(username)
        client.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()?.items
                }
                _isLoading.value = false
            }
            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                Log.e("onFailure:", t.message!!)
                _isLoading.value = false
            }
        })
    }

    fun getTheme(): LiveData<Boolean> = settings.getTheme().asLiveData()

    suspend fun setTheme(isInDarkMode: Boolean) {
        settings.setTheme(isInDarkMode)
    }
}