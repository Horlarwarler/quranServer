package com.crezent.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class VersionDatabaseModel(
    val versionCode: Double,
    @BsonId
    val _id: Id<VersionDatabaseModel>? = null
)
