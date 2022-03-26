package com.dicoding.fauzan.github

import android.os.Parcelable
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

    var repository: Int,

    var company: String,

    var followers: Int,

    var following: Int,
    /*
    @ColumnInfo(name = "favorited")
    var isFavorited: Boolean

     */
): Parcelable
