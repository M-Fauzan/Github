package com.dicoding.fauzan.github.service

import com.dicoding.fauzan.github.data.FollowerResponseItem
import com.dicoding.fauzan.github.data.ItemsItem
import com.dicoding.fauzan.github.data.UserDetailResponse
import com.dicoding.fauzan.github.data.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun search(@Query("q") username: String) : Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun examine(@Path("username") username: String) : Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun listFollowers(@Path("username") username: String) : Call<List<FollowerResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun listFollowing(@Path("username") username: String) : Call<List<FollowerResponseItem>>
}