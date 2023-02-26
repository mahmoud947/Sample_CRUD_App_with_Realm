package com.example.data.database

import com.example.data.local.entity.UserEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object RealmDatabase {

    fun getInstance(): Realm {
        synchronized(this) {
            val config = RealmConfiguration.Builder(
                schema = setOf(
                    UserEntity::class
                )
            ).compactOnLaunch()
                .build()
            return Realm.open(config)
        }
    }
}