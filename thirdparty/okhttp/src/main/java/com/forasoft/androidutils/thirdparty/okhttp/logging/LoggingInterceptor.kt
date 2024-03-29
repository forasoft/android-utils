package com.forasoft.androidutils.thirdparty.okhttp.logging

import android.util.Log
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okio.Buffer
import okio.GzipSource
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * [Interceptor], logging requests, responses and request exceptions in an opinionated way.
 *
 * Since interceptors are called in the order they are added to [OkHttpClient], you'd probably want
 * to add this one last; this will ensure the log messages match closely with what's being actually
 * sent and received by the client.
 *
 * Example log messages.
 * Request:
 * ```
 * --> POST https://example.com/endpoint
 * Content-Type: application/x-www-form-urlencoded
 * parameter=417
 * --> END POST
 * ```
 *
 * Response:
 * ```
 * <-- 200 OK https://example.com/endpoint took 764ms
 * Server: nginx/1.18.0 || Date: Wed, 14 Sep 2022 16:00:59 GMT || Content-Type: application/json
 * {"ok": "ok"}
 * <-- END HTTP
 * ```
 *
 * Exception:
 * ```
 * <-- HTTP FAILED: java.net.SocketTimeoutException: timeout
 * ```
 */
public object LoggingInterceptor : Interceptor() {

    override fun onRequest(info: RequestInfo): Request {
        val (request, connection) = info
        val message = buildString {
            append("--> ")
            append("${request.method} ")
            append("${request.url} ")
            if (connection != null) append("${connection.protocol()} ")
            appendLine()
            val contentType = request.getNonHeaderContentType()
            val contentLength = request.getNonHeaderContentLength()
            appendHeaders(
                builder = this,
                headers = request.headers,
                contentType = contentType,
                contentLength = contentLength
            )
            appendBody(this, request.body, request.headers)
            append("--> END ")
            append(request.method)
        }
        Log.v(LOG_TAG, message)
        return super.onRequest(info)
    }

    override fun onResponse(info: ResponseInfo): Response {
        val (duration, response) = info
        val message = buildString {
            append("<-- ")
            append("${response.code} ")
            if (response.message.isNotEmpty()) append("${response.message} ")
            append("${response.request.url} ")
            append("took ${duration}ms ")
            appendLine()
            appendHeaders(this, response.headers)
            if (response.promisesBody()) {
                appendBody(this, response)
                appendLine()
            }
            append("<-- END HTTP")
        }
        Log.v(LOG_TAG, message)
        return super.onResponse(info)
    }

    override fun onError(exception: Exception) {
        Log.e(LOG_TAG, "<-- HTTP FAILED", exception)
        super.onError(exception)
    }

    private fun appendHeaders(
        builder: StringBuilder,
        headers: Headers?,
        contentType: MediaType? = null,
        contentLength: Long? = null,
    ) {
        if (headers == null) return
        val headerStrings = buildList(headers.size + 2) {
            if (contentType != null)
                add(getHeaderString("Content-Type", contentType.toString()))
            if (contentLength != null && contentLength != -1L)
                add(getHeaderString("Content-Length", contentLength.toString()))
            headers.iterator().forEach { (key, value) ->
                add(getHeaderString(key, value))
            }
        }
        if (headerStrings.isNotEmpty()) builder.appendLine(
            headerStrings.joinToString(
                HEADER_ENTRY_SEPARATOR
            )
        )
    }

    private fun getHeaderString(key: String, value: String): String {
        return "$key$HEADER_NAME_VALUE_SEPARATOR$value"
    }

    private fun appendBody(builder: StringBuilder, body: RequestBody?, headers: Headers?) {
        if (body == null) return
        builder.append(
            when {
                headers?.let { bodyHasUnknownEncoding(it) } == true -> "(encoded body omitted)"
                body.isDuplex() -> "(duplex request body omitted)"
                body.isOneShot() -> "(one-shot body omitted)"
                else -> getBody(body)
            }
        )
        builder.appendLine()
    }

    private fun getBody(body: RequestBody): String {
        if (body is MultipartBody) {
            return buildString {
                body.parts.iterator().forEach { part ->
                    val buffer = Buffer()
                    part.body.writeTo(buffer)
                    appendHeaders(
                        builder = this,
                        headers = part.headers,
                        contentType = part.body.contentType(),
                        contentLength = part.body.contentLength()
                    )
                    appendBody(this, part.body, part.headers)
                }
            }
        } else {
            val buffer = Buffer()
            body.writeTo(buffer)
            var contentLength = body.contentLength()
            if (contentLength == -1L) contentLength = buffer.size
            return read(
                buffer,
                contentType = body.contentType(),
                contentLength = contentLength
            )
        }
    }

    private fun read(buffer: Buffer, contentType: MediaType?, contentLength: Long): String {
        val charset: Charset =
            contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

        return if (buffer.isProbablyUtf8()) {
            buffer.readString(charset)
        } else {
            "(binary ${contentLength}-byte body omitted)"
        }
    }

    private fun appendBody(builder: StringBuilder, response: Response) {
        builder.append(
            when {
                response.body == null -> return
                bodyHasUnknownEncoding(response.headers) -> "(encoded body omitted)"
                else -> getBody(response)
            }
        )
    }

    private fun getBody(response: Response): String {
        val body = response.body ?: return ""
        val source = body.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        var buffer = source.buffer

        var gzippedLength: Long? = null
        if ("gzip".equals(response.headers["Content-Encoding"], ignoreCase = true)) {
            gzippedLength = buffer.size
            GzipSource(buffer.clone()).use { gzippedResponseBody ->
                buffer = Buffer()
                buffer.writeAll(gzippedResponseBody)
            }
        }

        val contentType = body.contentType()
        val charset: Charset =
            contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

        return when {
            !buffer.isProbablyUtf8() -> "(binary ${buffer.size}-byte body omitted)"
            body.contentLength() != 0L -> buffer.clone().readString(charset)
            gzippedLength != null -> "(${buffer.size}-byte, $gzippedLength-gzipped-byte body)"
            else -> "(${buffer.size}-byte body)"
        }
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun Request.getNonHeaderContentType(): MediaType? {
        val contentType = body?.contentType() ?: return null
        val isHeaderPresent = headers["Content-Type"] != null
        return if (isHeaderPresent) null else contentType
    }

    private fun Request.getNonHeaderContentLength(): Long? {
        val contentLength = body?.contentLength() ?: return null
        val isHeaderPresent = body?.contentLength() != null
        return if (isHeaderPresent) null else contentLength
    }

    private const val HEADER_NAME_VALUE_SEPARATOR = ": "
    private const val HEADER_ENTRY_SEPARATOR = " || "
    private const val LOG_TAG = "OkHttpLogging"
}
