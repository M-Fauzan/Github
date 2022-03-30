package com.dicoding.fauzan.github.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.fauzan.github.data.FollowerResponseItem
import com.dicoding.fauzan.github.databinding.ItemRowFollowerBinding

class ListFollowerAdapter(var list: List<FollowerResponseItem>) :
    RecyclerView.Adapter<ListFollowerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemRowFollowerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvFollowerUsername.text = list[position].login
        Glide.with(holder.itemView.context)
            .load(list[position].avatarUrl)
            .circleCrop()
            .placeholder(android.R.drawable.menuitem_background)
            .error(android.R.drawable.stat_notify_error)
            .timeout(5000)
            .into(holder.binding.imgFollowerAvatar)
        Log.d("onBindViewHolder: ", list[position].login)
        Log.d("onBindViewHolder: ", list[position].avatarUrl)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: ItemRowFollowerBinding) : RecyclerView.ViewHolder(binding.root)
}