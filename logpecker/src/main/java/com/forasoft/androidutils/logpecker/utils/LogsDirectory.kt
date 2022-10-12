package com.forasoft.androidutils.logpecker.utils

import android.content.Context
import java.io.File

private const val LOGS_DIRECTORY = "log_pecker"

/**
 * Creates and returns the directory for storing log files.
 */
internal fun getLogsDirectory(context: Context): File {
    val cacheDirectory = context.externalCacheDir ?: context.cacheDir
    val logsDirectory = File(cacheDirectory, LOGS_DIRECTORY)
    ensureDirectory(logsDirectory)
    return logsDirectory
}

/**
 * Assures that the given directory exists.
 *
 * If there is a file with the same name, it will be deleted.
 */
internal fun ensureDirectory(directory: File) {
    if (directory.isFile) directory.delete()
    if (!directory.isDirectory) directory.mkdirs()
}
