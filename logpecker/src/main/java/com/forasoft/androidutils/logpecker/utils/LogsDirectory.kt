package com.forasoft.androidutils.logpecker.utils

import android.content.Context
import java.io.File

private const val LOGS_DIRECTORY = "log_pecker"

internal fun getLogsDirectory(context: Context): File {
    val cacheDir = context.externalCacheDir ?: context.cacheDir
    val logDir = File("${cacheDir.absolutePath}/$LOGS_DIRECTORY")
    logDir.mkdir()
    return logDir
}
