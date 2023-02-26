package com.example.realmsample.ui.main

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.models.User
import com.example.realmsample.R
import com.example.realmsample.databinding.ActivityMainBinding
import com.example.realmsample.ui.main.adapters.UsersAdapters
import com.google.android.material.snackbar.Snackbar
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


        usersAdapters = UsersAdapters(object : UsersAdapters.Interaction {
            override fun onItemSelected(position: Int, item: User) {
                binding.etName.setText(item.name)
                binding.etId.setText(item.id)
            }

            override fun onDeleteClicked(item: User) {
                viewModel.deleteUser(item)
                Snackbar.make(
                    binding.root,
                    "User:${item.name} deleted Successfully",
                    Snackbar.LENGTH_SHORT
                ).setAction("Undo") {
                    viewModel.undo()
                }.show()
            }
        })
        userLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvUsers.apply {
            adapter = usersAdapters
            layoutManager = userLayout
        }

        binding.btnAdd.setOnClickListener {
            viewModel.addUser(binding.etName.text.toString())
        }

        binding.btnUpdate.setOnClickListener {
            if (!TextUtils.isEmpty(binding.etId.text)) {
                viewModel.updateUser(
                    User(
                        id = binding.etId.text.toString(),
                        name = binding.etName.text.toString()
                    )
                )
            }
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.filterUsers(p0.toString())
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        binding.searchView.setOnCloseListener {
            binding.searchView.setQuery("", false)
            viewModel.getUsers()
            false
        }


        viewModel.users.observe(this) {
            usersAdapters.submitList(it)
        }

    }
}