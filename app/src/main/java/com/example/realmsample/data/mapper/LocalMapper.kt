package com.example.realmsample.data.mapper

import com.example.realmsample.data.local.entity.UserEntity
import com.example.realmsample.models.User

fun UserEntity.toDomain(): User =
    User(id = _id.toString(), name = name)


fun List<UserEntity>.toDomain():List<User> = map { it.toDomain() }