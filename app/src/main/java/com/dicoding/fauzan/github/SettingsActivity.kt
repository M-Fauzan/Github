package com.dicoding.fauzan.github

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.fauzan.github.databinding.ActivitySettingsBinding
import com.dicoding.fauzan.github.datastore.Settings
import com.dicoding.fauzan.github.viewmodel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel
    private val Context.datastore by preferencesDataStore(name = "settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = Settings.getInstance(datastore)!!
        settingsViewModel = ViewModelProvider(this, ViewModelFactory(settings))
            .get(SettingsViewModel::class.java)

        binding.switchSettingsDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setTheme(isChecked)
        }
        settingsViewModel.getTheme().observe(this, { isInDarkMode ->
            if (isInDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchSettingsDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchSettingsDarkMode.isChecked = false
            }
        })
    }
}