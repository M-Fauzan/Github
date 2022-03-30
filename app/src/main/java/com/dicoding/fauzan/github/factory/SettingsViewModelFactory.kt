package com.dicoding.fauzan.github.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.fauzan.github.datastore.Settings
import com.dicoding.fauzan.github.viewmodel.SettingsViewModel
import java.lang.IllegalArgumentException

class SettingsViewModelFactory(private val settings: Settings) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(settings) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}