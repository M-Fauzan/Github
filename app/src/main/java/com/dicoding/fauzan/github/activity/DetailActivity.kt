package com.dicoding.fauzan.github.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.fauzan.github.User
import com.dicoding.fauzan.github.adapter.UserStateAdapter
import com.dicoding.fauzan.github.databinding.ActivityDetailBinding
import com.dicoding.fauzan.github.viewmodel.FollowerViewModel
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: FollowerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_KEY) as User
        val usernameText = "AKA ${user.username}"
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatar)
                .circleCrop()
                .into(binding.imgUserAvatar)
            tvDetailName.text = user.name
            tvDetailUsername.text = usernameText
            tvDetailLocation.text = user.location
            tvDetailRepository.text = user.repository.toString()
            tvDetailCompany.text = user.company
            tvDetailFollowers.text = user.followers.toString()
            tvDetailFollowing.text = user.following.toString()
        }
        val userStateAdapter = UserStateAdapter(this)
        userStateAdapter.username = user.username
        val viewPager2 = binding.vpDetail.apply {
            adapter = userStateAdapter
        }
        TabLayoutMediator(binding.tabDetail, viewPager2) {
            tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

    }
    companion object {
        const val EXTRA_KEY = "extra_key"
        val TAB_TITLES = arrayOf("Followers", "Following")
    }
}