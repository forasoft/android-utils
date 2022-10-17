package com.forasoft.androidutils.logpecker.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

@Suppress("ForbiddenComment")
// TODO: Replace with platform.android module implementation when publishing the library

internal fun Context.viewFile(
    file: File,
    fileProviderAuthority: String,
    mimeType: String = "*/*",
    chooserTitle: String? = null,
) {
    val uri = FileProvider.getUriForFile(this, fileProviderAuthority, file)
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        setDataAndType(uri, mimeType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, chooserTitle))
}

internal fun Context.shareFiles(
    files: List<File>,
    fileProviderAuthority: String,
    mimeType: String = "*/*",
) {
    if (files.isEmpty()) return

    val uris = files.map {
        FileProvider.getUriForFile(this, fileProviderAuthority, it)
    }
    val intent = Intent().apply {
        if (uris.size == 1) {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uris.firstOrNull())
        } else {
            action = Intent.ACTION_SEND_MULTIPLE
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
        }
        type = mimeType
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, null))
}
