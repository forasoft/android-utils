package com.forasoft.androidutils.logcollector

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class LogCollector(private val context: Context) {

    suspend fun launch() = withContext(Dispatchers.IO) {
        val directory = getLogsDirectory()
        val fileName = getFileName()
        val file = File(directory, "$fileName.txt")
        val writer = file.bufferedWriter()

        val runtime = Runtime.getRuntime()
        runtime.exec(COMMAND_CLEAR_LOGCAT)
        runtime.exec(COMMAND_RUN_LOGCAT)
            .inputStream
            .bufferedReader()
            .useLines { lines ->
                lines.forEach { line ->
                    writer.appendLine(line)
                }
            }
    }

    private fun getLogsDirectory(): File {
        val cacheDir = context.externalCacheDir ?: context.cacheDir
        val logDir = File("${cacheDir.absolutePath}/$LOGS_DIRECTORY")
        logDir.mkdir()
        return logDir
    }

    private fun getFileName(): String {
        val date = Date(System.currentTimeMillis())
        val formatter = SimpleDateFormat(DATE_TIME_PATTERN, Locale.US)
        return formatter.format(date)
    }

    companion object {
        private const val LOGS_DIRECTORY = "log_collector"
        private const val DATE_TIME_PATTERN = "dd.MM.yyyy-HH:mm.SSS"

        private const val COMMAND_CLEAR_LOGCAT = "logcat -c"
        private const val COMMAND_RUN_LOGCAT = "logcat"
    }

}
