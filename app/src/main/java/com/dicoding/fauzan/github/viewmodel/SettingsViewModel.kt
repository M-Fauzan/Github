package com.dicoding.fauzan.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.fauzan.github.datastore.Settings
import kotlinx.coroutines.launch

class SettingsViewModel(private val settings: Settings): ViewModel() {

    fun getTheme(): LiveData<Boolean> = settings.getTheme().asLiveData()

    fun setTheme(isInDarkMode: Boolean) {
        viewModelScope.launch {
            settings.setTheme(isInDarkMode)
        }
    }

}