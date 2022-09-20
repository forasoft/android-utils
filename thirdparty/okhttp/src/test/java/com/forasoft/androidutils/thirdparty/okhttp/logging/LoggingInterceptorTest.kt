package com.forasoft.androidutils.thirdparty.okhttp.logging

import android.util.Log
import com.google.common.truth.Truth
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Before
import timber.log.Timber
import java.net.ConnectException

class LoggingInterceptorTest {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var logs: MutableList<LogMessage>
    private val interceptor = LoggingInterceptor

    @Before
    fun before() {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        logs = mutableListOf()

        plantListLoggingTree(logs)
    }

    @org.junit.Test
    fun onRequest_logsCorrectMessage() {
        val bodyValue = "name" to "value"
        val headerValue = "headerName" to "headerValue"
        val endpoint = "endpoint"

        val body = FormBody.Builder()
            .add(bodyValue.first, bodyValue.second)
            .build()

        val request = Request.Builder()
            .post(body)
            .url("$DOMAIN$endpoint")
            .addHeader(headerValue.first, headerValue.second)
            .build()

        kotlin.runCatching { okHttpClient.newCall(request).execute() }

        val expectedMessage = LogMessage(
            priority = Log.DEBUG,
            tag = LoggingInterceptor.LOG_TAG,
            message = "--> POST $DOMAIN$endpoint \n" +
                    "Content-Type: application/x-www-form-urlencoded || ${headerValue.first}: ${headerValue.second}\n" +
                    "${bodyValue.first}=${bodyValue.second}\n" +
                    "--> END POST"
        )
        Truth.assertThat(logs).contains(expectedMessage)
    }

    @org.junit.Test
    fun onError_logsCorrectMessage() {
        val request = Request.Builder()
            .get()
            .url(DOMAIN)
            .build()

        kotlin.runCatching { okHttpClient.newCall(request).execute() }

        val exceptedMessage = LogMessage(
            priority = Log.DEBUG,
            tag = LoggingInterceptor.LOG_TAG,
            message = "<-- HTTP FAILED",
            throwable = null
        )

        val actualMessage = logs.firstOrNull {
            it.message.startsWith(exceptedMessage.message)
                    && it.priority == exceptedMessage.priority
                    && it.tag == exceptedMessage.tag
                    && it.throwable is ConnectException
        }
        Truth.assertThat(actualMessage).isNotNull()
    }

    private fun plantListLoggingTree(list: MutableList<LogMessage>) {
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                list.add(LogMessage(priority, tag, message, t))
            }
        })
    }

    data class LogMessage(
        val priority: Int,
        val tag: String?,
        val message: String,
        val throwable: Throwable? = null,
    )

    companion object {
        private const val DOMAIN = "https://localhost:8080/"
    }
}
