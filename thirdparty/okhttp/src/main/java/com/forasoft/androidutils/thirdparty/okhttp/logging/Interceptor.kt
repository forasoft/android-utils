package com.forasoft.androidutils.thirdparty.okhttp.logging

import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Thin abstraction above [Interceptor]
 *
 * @constructor Create empty Interceptor
 */
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

    /**
     * Called when [Request] is intercepted (before it is executed).
     *
     * Override to implement request logic or to modified the intercepted [Request]
     *
     * @param info intercepted request information
     * @return request to be performed
     */
    open fun onRequest(info: RequestInfo): Request {
        return info.request
    }

    /**
     * Called after [Response] is received (before it is sent to consumers).
     *
     * @param info intercepted response information
     * @return response to be passed to consumers
     */
    open fun onResponse(info: ResponseInfo): Response {
        return info.response
    }

    /**
     * Called when request execution results in [Exception].
     *
     * @param exception exception thrown during request execution
     */
    open fun onError(exception: Exception) = Unit
}

/**
 * Intercepted request information
 *
 * @property request intercepted request
 * @property connection [Connection] the [request] will be executed on
 */
data class RequestInfo(
    val request: Request,
    val connection: Connection?,
)

/**
 * Intercepted response information
 *
 * @property requestDurationMillis duration, in millis, between sending the [Request] and receiving the [Response]
 * @property response intercepted response
 */
data class ResponseInfo(
    val requestDurationMillis: Long,
    val response: Response,
)
