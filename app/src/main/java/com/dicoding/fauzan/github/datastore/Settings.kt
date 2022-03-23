package com.dicoding.fauzan.github.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Settings private constructor(private val dataStore: DataStore<Preferences>){

    private val THEME_KEY = booleanPreferencesKey("theme_key")

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { settings ->
            settings[THEME_KEY] ?: false
        }
    }

    suspend fun setTheme(isInDarkMode: Boolean) {
        dataStore.edit { settings ->
            settings[THEME_KEY] = isInDarkMode
        }
    }

    companion object {
        @Volatile
        private var instance: Settings? = null

        fun getInstance(dataStore: DataStore<Preferences>): Settings? {
            return instance ?: synchronized(this) {
                instance = Settings(dataStore)
                instance
            }
        }
    }
}