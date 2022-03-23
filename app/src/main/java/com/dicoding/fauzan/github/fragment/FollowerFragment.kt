package com.dicoding.fauzan.github.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fauzan.github.ListFollowerAdapter
import com.dicoding.fauzan.github.databinding.FragmentFollowerBinding
import com.dicoding.fauzan.github.viewmodel.FollowerViewModel


/*
    Follower and following fragment
 */
class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    val binding get() = _binding!!

    private val viewModel: FollowerViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = arguments?.getInt(SECTION_NUMBER, -1)
        val username = arguments?.getString(USERNAME, "") as String


        binding.rvDetailFollowers.layoutManager = LinearLayoutManager(view.context)
        binding.rvDetailFollowers.setHasFixedSize(false)

        // binding.tvFollowerTest.text = getString(R.string.content_one_fragment, arg)
        // binding.rvDetailFollowers.adapter = ListFollowerAdapter(listOf())
        viewModel.loading.observe(this, { isLoading ->
            binding.pbFollower.visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        })
        viewModel.listFollowing(username)
        viewModel.listFollowers(username)
        when (arg) {
            0 -> viewModel.followerList.observe(this, {
                binding.rvDetailFollowers.adapter = ListFollowerAdapter(it)
            })
            1 -> viewModel.followingList.observe(this, {
                binding.rvDetailFollowers.adapter = ListFollowerAdapter(it)
            })
            else -> Toast.makeText(view.context, "Data not found", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        const val SECTION_NUMBER = "section_number"
        const val USERNAME = "username"
    }
}