package com.dicoding.fauzan.github.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.fauzan.github.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE favorite = 1")
    fun getFavoriteUsers(): LiveData<List<User>>

    @Query("SELECT EXISTS (SELECT * FROM user WHERE username = :username AND favorite = 1)")
    suspend fun isFavorite(username: String): Boolean
}