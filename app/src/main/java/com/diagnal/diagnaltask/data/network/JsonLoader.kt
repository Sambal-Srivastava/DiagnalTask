package com.diagnal.diagnaltask.data.network

import android.content.Context
import java.io.IOException

class JsonLoader(private val context: Context) {

    fun loadJsonFromAsset(fileName: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }
}
