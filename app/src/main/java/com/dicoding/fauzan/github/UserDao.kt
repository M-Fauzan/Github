package com.dicoding.fauzan.github

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY username")
    fun getUserList(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserList(userList: List<User>)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE favorited = 1")
    fun getFavoriteUsers(): LiveData<List<User>>
}