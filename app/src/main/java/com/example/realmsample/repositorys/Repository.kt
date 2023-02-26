package com.example.realmsample.repositorys

import com.example.realmsample.data.local.entity.UserEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject


class Repository @Inject constructor(
    private val realm: Realm
) {

    fun getData(): Flow<List<UserEntity>> = realm.query<UserEntity>().asFlow().map { it.list }

    suspend fun addUser(user: UserEntity) =
        realm.write {
            copyToRealm(user)
        }

}