package com.forasoft.androidutils.thirdparty.okhttp.logging

import okhttp3.*
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

abstract class Interceptor : Interceptor {

    final override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val connection = chain.connection()
        val request = onRequest(
            RequestInfo(
                request = initialRequest,
                connection = connection
            )
        )

        val startTimestamp = System.nanoTime()
        val initialResponse: Response
        @Suppress("TooGenericExceptionCaught")
        try {
            initialResponse = chain.proceed(request)
        } catch (e: Exception) {
            onError(e)
            throw e
        }

        val requestDurationMillis =
            TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimestamp)

        return onResponse(
            ResponseInfo(
                response = initialResponse,
                requestDurationMillis = requestDurationMillis
            )
        )
    }

    open fun onRequest(info: RequestInfo): Request {
        return info.request
    }

    open fun onResponse(info: ResponseInfo): Response {
        return info.response
    }

    open fun onError(exception: Exception) = Unit
}

data class RequestInfo(
    val request: Request,
    val connection: Connection?,
)

data class ResponseInfo(
    val requestDurationMillis: Long,
    val response: Response,
)
