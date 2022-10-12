package com.forasoft.androidutils.logpecker.utils

import android.content.Context
import java.io.File

private const val LOGS_DIRECTORY = "log_pecker"

internal fun getLogsDirectory(context: Context): File {
    val cacheDirectory = context.externalCacheDir ?: context.cacheDir
    val logsDirectory = File(cacheDirectory, LOGS_DIRECTORY)
    ensureDirectory(logsDirectory)
    return logsDirectory
}

internal fun ensureDirectory(directory: File) {
    if (directory.isFile) directory.delete()
    if (!directory.isDirectory) directory.mkdirs()
}
