package com.forasoft.androidutils.logpecker.ui

import android.net.Uri
import androidx.core.net.toUri
import java.io.File

internal data class FileItem(
    val uri: Uri,
    val name: String,
) {

    companion object {
        fun fromFile(file: File): FileItem = FileItem(
            uri = file.toUri(),
            name = file.name,
        )
    }
}
