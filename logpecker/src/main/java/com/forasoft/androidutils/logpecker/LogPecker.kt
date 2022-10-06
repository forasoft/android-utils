package com.forasoft.androidutils.logpecker

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

internal class LogPecker(context: Context) {

    private val fileMaxCount =
        context.resources.getInteger(R.integer.forasoftandroidutils_log_pecker_file_max_count)
    private val fileMaxSize =
        context.resources.getInteger(R.integer.forasoftandroidutils_log_pecker_file_max_size_bytes)

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private val directory = createDirectory(context)

    private var currentFile: File? = null
    private var currentFileWriter: BufferedWriter? = null

    private var linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK

    fun start() {
        coroutineScope.launch(Dispatchers.IO) {
            Timber.v("LogPecker is running")
            createNewFile()
            deleteOldFiles()

            clearLogcat()
            runLogcat()
                .inputStream
                .bufferedReader()
                .useLines { writeLines(it) }
        }
    }

    private fun writeLines(lines: Sequence<String>) {
        lines.forEach { line ->
            writeLine(line)
            if (linesLeftBeforeFileSizeCheck <= 0) checkCurrentFileSize()
        }
    }

    private fun writeLine(line: String) {
        currentFileWriter?.appendLine(line)
        linesLeftBeforeFileSizeCheck -= 1
    }

    private fun clearLogcat() {
        Runtime.getRuntime().exec(RuntimeCommand.LOGCAT_CLEAR)
    }

    private fun runLogcat(): Process {
        return Runtime.getRuntime().exec(RuntimeCommand.LOGCAT_RUN)
    }

    private fun checkCurrentFileSize() {
        val size = currentFile?.length()
        if (size == null || size > fileMaxSize) {
            currentFileWriter?.close()
            createNewFile()
            deleteOldFiles()
        }
        linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK
    }

    private fun deleteOldFiles() {
        val files = directory.listFiles()
        if (files == null || files.size <= fileMaxCount) return
        files.sortBy(File::lastModified)
        repeat(files.size - fileMaxCount) { index ->
            files[index].delete()
        }
    }

    private fun createNewFile() {
        val fileName = createFileName()
        val file = File(directory, "$fileName.txt")
        val fileWriter = file.bufferedWriter()
        currentFile = file
        currentFileWriter = fileWriter
    }

    private fun createFileName(): String {
        val date = Date()
        val formatter = SimpleDateFormat(FILE_DATE_TIME_FORMAT, Locale.US)
        return formatter.format(date)
    }

    private fun createDirectory(context: Context): File {
        val cacheDir = context.externalCacheDir ?: context.cacheDir
        val logDir = File("${cacheDir.absolutePath}/$LOGS_DIRECTORY")
        logDir.mkdir()
        return logDir
    }

    companion object {
        const val FILE_DATE_TIME_FORMAT = "dd.MM.yyyy-HH:mm:ss"
        private const val LOGS_DIRECTORY = "logs"
        private const val WRITTEN_LINES_PER_FILE_SIZE_CHECK = 100
    }

}
