package com.crezent.data.FileHelper

import com.crezent.Util.JsonSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileReader

class JsonHelperImplementation : JsonHelperInterface {

    override fun <T> readFromJsonFile(fileName: String, contentSerializer: KSerializer<T>): List<T> {
        val fileToRead = File(fileName)
        val fileReader = FileReader(fileToRead)
        val bufferedReader = BufferedReader(fileReader)
        val stringBuilder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        val decodedString = decodeFromString(JsonSerializer.serializer(contentSerializer), stringBuilder.toString())
        return decodedString.data
    }

    override fun <T> writeToJsonFile(fileName: String, contentKSerializer: KSerializer<T>, data: List<T>) {
        val fileToUpdate = File(fileName)
        fileToUpdate.delete()
        File(fileName)
        val serializedData = JsonSerializer(data = data)
        val stringToEncode = Json.encodeToString(JsonSerializer.serializer(contentKSerializer), serializedData)
        val inputStream = ByteArrayInputStream(stringToEncode.encodeToByteArray())
        val outputStream = fileToUpdate.outputStream()
        inputStream.use {
            input ->
            outputStream.use {
                output ->
                input.copyTo(output)
            }
        }
        inputStream.close()
        outputStream.close()
        outputStream.flush()
    }



}