package com.dicoding.fauzan.github.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fauzan.github.adapter.ListSearchAdapter
import com.dicoding.fauzan.github.R
import com.dicoding.fauzan.github.Result
import com.dicoding.fauzan.github.databinding.ActivityMainBinding
import com.dicoding.fauzan.github.datastore.Settings
import com.dicoding.fauzan.github.factory.MainViewModelFactory
import com.dicoding.fauzan.github.factory.SettingsViewModelFactory
import com.dicoding.fauzan.github.viewmodel.MainViewModel
import com.dicoding.fauzan.github.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val Context.datastore by preferencesDataStore(name = "settings")
    private lateinit var mainViewModel: MainViewModel
    private lateinit var settingsViewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainViewModel = ViewModelProvider(this, MainViewModelFactory.getInstance(this))
            .get(MainViewModel::class.java)

        val settings = Settings.getInstance(datastore)!!
        settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(settings))
            .get(SettingsViewModel::class.java)

        settingsViewModel.getTheme().observe(this, { isInDarkMode ->
            if (isInDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search people"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.search(query).observe(this@MainActivity, { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.pbMain.visibility = View.VISIBLE
                                binding.rvMainUser.visibility = View.GONE
                            }
                            is Result.Success -> {
                                binding.pbMain.visibility = View.GONE
                                binding.rvMainUser.visibility = View.VISIBLE
                                Log.d("MainActivity", result.data.toString())
                                val listSearchAdapter = ListSearchAdapter { user ->
                                    if (!user.isFavorite) {
                                        mainViewModel.insertUser(user)
                                        Toast.makeText(this@MainActivity,
                                            "${user.username} telah ditambahkan. Silahkan cek di halaman favorite",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                                binding.rvMainUser.apply {
                                    layoutManager = LinearLayoutManager(this@MainActivity)
                                    setHasFixedSize(false)
                                    adapter = listSearchAdapter
                                }
                                listSearchAdapter.submitList(result.data)

                            }
                            is Result.Error -> {
                                binding.pbMain.visibility = View.GONE
                                Toast.makeText(
                                    this@MainActivity,
                                    result.error,
                                    Toast.LENGTH_SHORT).show()
                            }

                        }
                    })
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                val settingsIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
            R.id.menu_favorite -> {
                val favoriteIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }



}