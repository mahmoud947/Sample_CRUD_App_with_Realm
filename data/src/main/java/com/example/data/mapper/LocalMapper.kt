package com.example.data.mapper

import com.example.data.local.entity.UserEntity
import com.example.data.models.User

fun UserEntity.toDomain(): User =
    User(id = _id.toHexString(), name = name)



fun List<UserEntity>.toDomain():List<User> = map { it.toDomain() }