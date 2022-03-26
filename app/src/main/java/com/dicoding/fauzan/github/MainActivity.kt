package com.dicoding.fauzan.github

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fauzan.github.databinding.ActivityMainBinding
import com.dicoding.fauzan.github.datastore.Settings
import com.dicoding.fauzan.github.viewmodel.MainViewModel
import com.dicoding.fauzan.github.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val Context.dataStore by preferencesDataStore(name = "settings")
    private lateinit var mainViewModel: MainViewModel

    private lateinit var listSearchAdapter: ListSearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = Settings.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(settings!!)).get(
            SettingsViewModel::class.java)


        val repository = UserRepository.getInstance()
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
        /*
        viewModel.userList.observe(this, { list ->
            binding.rvMainUser.adapter = ListSearchAdapter(list as ArrayList<ItemsItem>)
        })

        viewModel.isLoading.observe(this, { isLoading ->
            binding.pbMain.visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        })


         */
        val listSearchAdapter = ListSearchAdapter {

        }
        listSearchAdapter.submitList(ArrayList<User>())
        binding.rvMainUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
            adapter = listSearchAdapter
        }
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
                            }
                            is Result.Success -> {
                                binding.pbMain.visibility = View.GONE
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
        }
        return super.onOptionsItemSelected(item)
    }



}