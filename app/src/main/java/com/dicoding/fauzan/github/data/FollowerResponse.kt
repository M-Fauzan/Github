package com.dicoding.fauzan.github.data

import com.google.gson.annotations.SerializedName

data class FollowerResponse(

	@field:SerializedName("FollowingResponse")
	val followerResponse: List<FollowerResponseItem>
)

data class FollowerResponseItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,


)
