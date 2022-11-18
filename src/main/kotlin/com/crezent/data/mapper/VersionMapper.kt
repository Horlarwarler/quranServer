package com.crezent.data.mapper

import com.crezent.data.models.VersionDatabaseModel
import com.crezent.domain.model.VersionModel

fun VersionDatabaseModel.convertToModel():VersionModel{
    return VersionModel(
        versionCode
    )
}

fun VersionModel.convertToDatabase():VersionDatabaseModel{
    return  VersionDatabaseModel(versionCode)
}
