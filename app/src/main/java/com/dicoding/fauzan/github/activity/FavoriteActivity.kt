package com.dicoding.fauzan.github.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fauzan.github.adapter.ListSearchAdapter
import com.dicoding.fauzan.github.databinding.ActivityFavoriteBinding
import com.dicoding.fauzan.github.factory.FavoriteViewModelFactory
import com.dicoding.fauzan.github.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var favoriteViewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = ViewModelProvider(this,
            FavoriteViewModelFactory.getInstance(this)).
            get(FavoriteViewModel::class.java)


        val listSearchAdapter = ListSearchAdapter { user ->
            if (user.isFavorite) {
                favoriteViewModel.deleteUser(user)
            }
        }
        binding.rvFavoriteUser.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(false)
            adapter = listSearchAdapter
        }
        favoriteViewModel.getFavoriteUsers().observe(this, {
            listSearchAdapter.submitList(it)
        })
    }
}