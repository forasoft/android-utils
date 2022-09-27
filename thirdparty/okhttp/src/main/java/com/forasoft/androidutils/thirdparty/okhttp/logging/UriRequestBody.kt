package com.forasoft.androidutils.thirdparty.okhttp.logging

import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

@Suppress("unused")
class UriRequestBody(
    private val contentType: MediaType?,
    private val contentResolver: ContentResolver,
    private val uri: Uri,
) : RequestBody() {
    override fun contentType(): MediaType? = contentType

    override fun writeTo(sink: BufferedSink) {
        contentResolver.openInputStream(uri).use { inputStream ->
            checkNotNull(inputStream) { "Can't open $uri input stream" }
            inputStream.source().use { sink.writeAll(it) }
        }
    }
}
