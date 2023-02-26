package com.example.realmsample.ui.main

import android.text.TextUtils
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

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    var deletedUser: User? = null

    init {
        getUsers()
    }


    fun addUser(mName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(User(name = mName))
        }
    }


    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData()
                .collect {
                    _users.postValue(it.toDomain())
                }
        }
    }

    fun filterUsers(name: String) {
        if (!TextUtils.isEmpty(name)) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.filterUsers(name).collect {
                    _users.postValue(it.toDomain())
                }
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePerson(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePerson(user.id)
            deletedUser = user
        }
    }

    fun undo() {
        deletedUser?.let {
            addUser(it.name)
        }
    }

}