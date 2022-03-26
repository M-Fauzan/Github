package com.dicoding.fauzan.github.service

import com.dicoding.fauzan.github.BuildConfig
import com.dicoding.fauzan.github.data.FollowerResponseItem
import com.dicoding.fauzan.github.data.ItemsItem
import com.dicoding.fauzan.github.data.UserDetailResponse
import com.dicoding.fauzan.github.data.UserSearchResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("search/users")
    suspend fun search(@Header("Authorization") authorization: String,
               @Query("q") username: String) : UserSearchResponse

    @GET("users/{username}")
    suspend fun examine(@Header("Authorization") authorization: String,
                @Path("username") username: String) : UserDetailResponse

    @GET("users/{username}/followers")
    fun listFollowers(@Header("Authorization") authorization: String,
                      @Path("username") username: String) : Call<List<FollowerResponseItem>>

    @GET("users/{username}/following")
    fun listFollowing(@Header("Authorization") authorization: String,
                      @Path("username") username: String) : Call<List<FollowerResponseItem>>
}