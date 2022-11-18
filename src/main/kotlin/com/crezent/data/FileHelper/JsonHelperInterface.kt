package com.crezent.data.FileHelper

import com.crezent.Util.JsonSerializer
import kotlinx.serialization.KSerializer

interface JsonHelperInterface {
    fun <T> readFromJsonFile(
        fileName:String,
        contentSerializer:KSerializer<T>

    ): List<T>

    fun <T> writeToJsonFile(fileName: String, contentKSerializer: KSerializer<T>, data:List<T>)


}