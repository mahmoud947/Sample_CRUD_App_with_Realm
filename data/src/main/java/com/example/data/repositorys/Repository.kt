package com.example.data.repositorys

import com.example.data.database.RealmDatabase
import com.example.data.local.entity.UserEntity
import com.example.data.models.User
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class Repository(
    private val realm: Realm
) {

    fun getData(): Flow<List<UserEntity>> = realm.query<UserEntity>().asFlow().map { it.list }

    suspend fun addUser(user: User) =
        realm.write {
            copyToRealm(UserEntity().apply {
                name = user.name
            })
        }


    companion object {
        fun getInstance(): Repository {
            return Repository(RealmDatabase.getInstance())
        }
    }

}