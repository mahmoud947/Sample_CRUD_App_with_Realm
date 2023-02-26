package com.example.realmsample.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realmsample.R
import com.example.realmsample.databinding.ActivityMainBinding
import com.example.realmsample.ui.main.adapters.UsersAdapters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapters: UsersAdapters
    private lateinit var userLayout: LinearLayoutManager
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        usersAdapters = UsersAdapters()
        userLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvUsers.apply {
            adapter = usersAdapters
            layoutManager = userLayout
        }

        viewModel.users.observe(this) {
            usersAdapters.submitList(it)
        }

        binding.btnAdd.setOnClickListener {
            viewModel.addUser(binding.etName.text.toString())
        }
    }
}