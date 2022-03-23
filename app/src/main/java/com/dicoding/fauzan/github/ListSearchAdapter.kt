package com.dicoding.fauzan.github

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.fauzan.github.data.ItemsItem
import com.dicoding.fauzan.github.data.UserDetailResponse
import com.dicoding.fauzan.github.databinding.ItemRowUserBinding
import com.dicoding.fauzan.github.service.RetrofitConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSearchAdapter(private val userList: ArrayList<ItemsItem>) : RecyclerView.Adapter<ListSearchAdapter.ListViewHolder>() {

    private val userModel = ArrayList<User>()
    class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (login, url, avatarUrl) = userList[position]
        val client = RetrofitConfig.getUserService().examine(login)
        runBlocking {
            launch(Dispatchers.IO) {
                client.enqueue(object : Callback<UserDetailResponse> {
                    override fun onResponse(
                        call: Call<UserDetailResponse>,
                        response: Response<UserDetailResponse>
                    ) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            val noInput = ""
                            userModel.add(
                                User(
                                    login,
                                    responseBody.name ?: noInput,
                                    avatarUrl,
                                    responseBody.location ?: noInput,
                                    responseBody.publicRepos.toString(),
                                    responseBody.company ?: noInput,
                                    responseBody.followers.toString(),
                                    responseBody.following.toString()
                                )
                            )
                            userModel.sortBy {it.username }
                        }
                    }

                    override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                        Log.e("onFailed: ", "${t.message}")
                    }
                })
            }
            delay(8000L)

        }


        holder.binding.apply {
            tvUsername.text = login
            Glide.with(holder.itemView.context).load(avatarUrl)
                .circleCrop()
                .placeholder(android.R.drawable.menuitem_background)
                .error(android.R.drawable.stat_notify_error)
                .timeout(5000)
                .into(imgAvatar)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_KEY, userModel[holder.adapterPosition])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = userList.size

}