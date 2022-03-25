package com.dicoding.fauzan.github

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey
    var username: String,

    var name: String,

    var avatar: String,

    var location: String,

    var repository: String,

    var company: String,

    var followers: String,

    var following: String,

    @ColumnInfo(name = "favorited")
    var isFavorited: Boolean
): Parcelable
