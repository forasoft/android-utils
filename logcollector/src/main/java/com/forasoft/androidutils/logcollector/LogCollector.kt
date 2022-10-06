package com.forasoft.androidutils.logcollector

import android.content.Context
import kotlinx.coroutines.*
import java.io.BufferedWriter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

class LogCollector(context: Context) {

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private val directory = createDirectory(context)

    private var currentFile: File? = null
    private var currentFileWriter: BufferedWriter? = null

    private var linesWritten = 0

    fun start() {
        coroutineScope.launch {
            deleteOldFiles()
            collectLogs()
        }
    }

    private fun createDirectory(context: Context): File {
        val cacheDir = context.externalCacheDir ?: context.cacheDir
        val logDir = File("${cacheDir.absolutePath}/$LOGS_DIRECTORY")
        logDir.mkdir()
        return logDir
    }

    private fun deleteOldFiles() {
        val files = directory.listFiles()
        if (files == null || files.size <= MAX_FILE_COUNT) return

        files.sortBy(File::lastModified)
        repeat(files.size - MAX_FILE_COUNT) { index ->
            files[index].delete()
        }
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
        val fileName = createFileName()
        val file = File(directory, "$fileName.txt")
        val fileWriter = file.bufferedWriter()
        currentFile = file
        currentFileWriter = fileWriter
    }

    private fun clearLogcat() {
        Runtime.getRuntime().exec(RuntimeCommand.CLEAR_LOGCAT)
    }

    private fun getLogcatProcess(): Process {
        return Runtime.getRuntime().exec(RuntimeCommand.RUN_LOGCAT)
    }

    private suspend fun writeLines(lines: Sequence<String>) {
        val job = coroutineContext.job
        lines.forEach { line ->
            currentFileWriter?.appendLine(line)
            linesWritten += 1

            if (linesWritten > WRITTEN_LINE_COUNT_PER_FILE_SIZE_CHECK) {
                val currentFileSize = currentFile?.length()
                if (currentFileSize == null || currentFileSize > FILE_MAX_SIZE) {
                    currentFileWriter?.close()
                    createNewFile()
                    deleteOldFiles()
                }
                linesWritten = 0
            }

            job.ensureActive()
        }
    }

    private fun createFileName(): String {
        val date = Date()
        val formatter = SimpleDateFormat(FILE_DATE_TIME_FORMAT, Locale.US)
        return formatter.format(date)
    }

    companion object {
        const val FILE_DATE_TIME_FORMAT = "dd.MM.yyyy-HH:mm:ss"

        private const val LOGS_DIRECTORY = "logs"

        private const val WRITTEN_LINE_COUNT_PER_FILE_SIZE_CHECK = 100
        private const val FILE_MAX_SIZE = 10_000_000L

        private const val MAX_FILE_COUNT = 10
    }

}
