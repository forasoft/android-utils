package com.forasoft.androidutils.logpecker

import android.content.Context
import java.io.File

private const val LOGS_DIRECTORY = "logs"

internal fun Context.getLogsDirectory(): File {
    val cacheDir = this.externalCacheDir ?: this.cacheDir
    val logDir = File("${cacheDir.absolutePath}/$LOGS_DIRECTORY")
    logDir.mkdir()
    return logDir
}
