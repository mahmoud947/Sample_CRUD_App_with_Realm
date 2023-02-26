package com.example.realmsample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mapper.toDomain
import com.example.data.models.User
import com.example.data.repositorys.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _users = MutableLiveData<List<com.example.data.models.User>>()
    val users: LiveData<List<com.example.data.models.User>> get() = _users

    init {
        getUsers()
    }


    fun addUser(mName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(User(name = mName))
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