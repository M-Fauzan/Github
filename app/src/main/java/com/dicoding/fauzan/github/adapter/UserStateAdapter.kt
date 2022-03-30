package com.dicoding.fauzan.github.adapter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.fauzan.github.fragment.FollowerFragment

class UserStateAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.SECTION_NUMBER, position)
            putString(FollowerFragment.USERNAME, username)
            Log.d("UserStateAdapter", "$position")
        }
        return fragment
    }
}