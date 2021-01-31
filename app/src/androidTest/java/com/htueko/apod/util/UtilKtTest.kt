package com.htueko.apod.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset


class UtilKtTest {

    @Test
    fun parseJson_returnStringTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inputStream: InputStream =
            InstrumentationRegistry.getInstrumentation().targetContext.resources.assets.open("nasa.json")
        val br = BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
        var jsonDataString: String? = null
        val builder = StringBuilder()
        while (br.readLine().also { jsonDataString = it } != null) {
            builder.append(jsonDataString)
        }
        val result = getJsonDataFromAsset(context, "nasa.json")
        assertThat(result).isEqualTo(String(builder))
    }

}