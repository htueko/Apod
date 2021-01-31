package com.htueko.apod.util

import android.content.Context
import android.content.res.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * convert file to StringBuilder
 */
fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    var inputStream: InputStream? = null
    val builder = StringBuilder()
    try {
        //jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        var jsonString: String? = null
        inputStream = context.resources.assets.open(fileName)
        val bufferedReader = BufferedReader(
            InputStreamReader(inputStream, "UTF-8")
        )
        while (bufferedReader.readLine().also { jsonString = it } != null) {
            builder.append(jsonString)
        }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    } finally {
        inputStream?.close()
    }
    return String(builder)
}

/**
 * Converts to dp.
 */
val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()