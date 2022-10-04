package com.forasoft.androidutils.logcollector

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.job
import kotlinx.coroutines.withContext
import java.io.BufferedWriter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

class LogCollector(context: Context) {

    private val directory = createDirectory(context)

    private val fileBaseName = createFileBaseName()
    private var filePart = 0

    private var currentFile: File? = null
    private var currentFileWriter: BufferedWriter? = null

    private var linesWritten = 0

    suspend fun start() = withContext(Dispatchers.IO) {
        collectLogs()
    }

    private fun createDirectory(context: Context): File {
        val cacheDir = context.externalCacheDir ?: context.cacheDir
        val logDir = File("${cacheDir.absolutePath}/$LOGS_DIRECTORY")
        logDir.mkdir()
        return logDir
    }

    private fun createFileBaseName(): String {
        val date = Date()
        val formatter = SimpleDateFormat(FILE_DATE_TIME_FORMAT, Locale.US)
        return formatter.format(date)
    }

    private suspend fun collectLogs() {
        createNewFile()
        clearLogcat()
        getLogcatProcess()
            .inputStream
            .bufferedReader()
            .useLines { writeLines(it) }
    }

    private fun createNewFile() {
        filePart += 1
        val fileName = createFileName()
        val file = File(directory, "$fileName.txt")
        val fileWriter = file.bufferedWriter()
        currentFile = file
        currentFileWriter = fileWriter
    }

    private fun clearLogcat() {
        Runtime.getRuntime().exec(COMMAND_CLEAR_LOGCAT)
    }

    private fun getLogcatProcess(): Process {
        return Runtime.getRuntime().exec(COMMAND_RUN_LOGCAT)
    }

    private suspend fun writeLines(lines: Sequence<String>) {
        val job = coroutineContext.job
        lines.forEach { line ->
            job.ensureActive()
            currentFileWriter?.appendLine(line)
            linesWritten += 1

            if (linesWritten > WRITTEN_LINE_COUNT_PER_FILE_SIZE_CHECK) {
                val currentFileSize = currentFile?.length()
                if (currentFileSize == null || currentFileSize > FILE_MAX_SIZE) {
                    currentFileWriter?.close()
                    createNewFile()
                }
                linesWritten = 0
            }
        }
    }

    private fun createFileName(): String {
        return if (filePart == 1) {
            fileBaseName
        } else {
            "$fileBaseName-$FILE_PART_SUFFIX$filePart"
        }
    }

    companion object {
        const val FILE_DATE_TIME_FORMAT = "dd.MM.yyyy-HH:mm:ss"
        const val FILE_PART_SUFFIX = "part"

        private const val LOGS_DIRECTORY = "logs"

        private const val COMMAND_CLEAR_LOGCAT = "logcat -c"
        private const val COMMAND_RUN_LOGCAT = "logcat"

        private const val WRITTEN_LINE_COUNT_PER_FILE_SIZE_CHECK = 100
        private const val FILE_MAX_SIZE = 10_000_000L
    }

}
