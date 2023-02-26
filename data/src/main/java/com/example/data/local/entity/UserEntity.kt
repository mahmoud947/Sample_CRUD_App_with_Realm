package com.example.data.local.entity

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UserEntity : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var name: String = ""
    var timeStamp: RealmInstant = RealmInstant.now()
}
