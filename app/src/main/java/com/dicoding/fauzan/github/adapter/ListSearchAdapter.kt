package com.dicoding.fauzan.github.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.fauzan.github.R
import com.dicoding.fauzan.github.User
import com.dicoding.fauzan.github.activity.DetailActivity
import com.dicoding.fauzan.github.databinding.ItemRowUserBinding

class ListSearchAdapter(private val onFavoriteClick: (User) -> Unit) :
    ListAdapter<User, ListSearchAdapter.ListViewHolder>(DIFFUTIL_CALLBACK) {

    class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvUsername.text = user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .circleCrop()
                .placeholder(android.R.drawable.menuitem_background)
                .error(android.R.drawable.stat_notify_error)
                .timeout(5000)
                .into(binding.imgAvatar)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_KEY, user)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        val imgFollowers = holder.binding.imgFollowers
        if (user.isFavorite) {
            imgFollowers.setImageDrawable(ContextCompat.getDrawable(
                imgFollowers.context, R.drawable.ic_baseline_favorite_24
            ))
        } else {
            imgFollowers.setImageDrawable(ContextCompat.getDrawable(
                imgFollowers.context, R.drawable.ic_baseline_favorite_border_24
            ))
        }
        holder.binding.imgFollowers.setOnClickListener {
            onFavoriteClick(user)
        }

    }

    companion object {
        val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}