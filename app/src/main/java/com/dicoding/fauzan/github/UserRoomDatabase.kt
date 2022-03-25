package com.dicoding.fauzan.github

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao?
    companion object {
        @Volatile
        private var instance: UserRoomDatabase? = null

        fun getInstance(context: Context) : UserRoomDatabase? {
            return instance ?: synchronized(this) {
                instance = Room.databaseBuilder(
                    context,
                    UserRoomDatabase::class.java,
                    "user_db"
                ).build()
                instance
            }
        }
    }
}