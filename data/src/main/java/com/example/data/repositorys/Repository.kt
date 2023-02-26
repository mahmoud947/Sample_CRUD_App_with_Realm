package com.example.data.repositorys

import com.example.data.database.RealmDatabase
import com.example.data.local.entity.UserEntity
import com.example.data.models.User
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId


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

    fun filterUsers(name: String): Flow<List<UserEntity>> {
        return realm.query<UserEntity>(query = "name CONTAINS[c] $0", name).asFlow().map { it.list }
    }

    suspend fun updatePerson(user: User) {
        realm.write {
            val queriedPerson =
                query<UserEntity>(query = "_id == $0", ObjectId(user.id)).first().find()
            queriedPerson?.name = user.name
        }
    }

    suspend fun deletePerson(id: String) {
        realm.write {
            val person = query<UserEntity>(query = "_id == $0", ObjectId(id)).first().find()
            try {
                person?.let { delete(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    companion object {
        fun getInstance(): Repository {
            return Repository(RealmDatabase.getInstance())
        }
    }

}