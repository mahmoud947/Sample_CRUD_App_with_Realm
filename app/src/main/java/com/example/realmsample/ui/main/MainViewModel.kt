package com.example.realmsample.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realmsample.data.local.entity.UserEntity
import com.example.realmsample.data.mapper.toDomain
import com.example.realmsample.models.User
import com.example.realmsample.repositorys.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        getUsers()
    }


    fun addUser(mName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(UserEntity().apply {
                name = mName
            })
        }
    }


    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData()
                .collect {
                    _users.postValue(it.toDomain())
                }
        }
    }

}