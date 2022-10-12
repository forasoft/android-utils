package com.forasoft.androidutils.logpecker

import android.content.Context
import android.util.Log
import com.forasoft.androidutils.logpecker.utils.ensureDirectory
import com.forasoft.androidutils.logpecker.utils.getLogsDirectory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

internal class LogPecker(context: Context) {

    private val fileMaxCount =
        context.resources.getInteger(R.integer.forasoftandroidutils_log_pecker_file_max_count)
    private val fileMaxSizeBytes =
        context.resources.getInteger(R.integer.forasoftandroidutils_log_pecker_file_max_size_bytes)

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private val directory = getLogsDirectory(context)

    private var currentFile: File? = null
    private var currentFileWriter: BufferedWriter? = null

    private var linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK

    fun start() {
        Log.d(TAG, "LogPecker is running")
        coroutineScope.launch(Dispatchers.IO) {
            switchToNewFile()
            clearLogcat()
            runLogcat()
                .inputStream
                .bufferedReader()
                .useLines(::writeLines)
        }
    }

    private fun writeLines(lines: Sequence<String>) {
        lines.forEach { line ->
            try {
                writeLine(line)
                if (linesLeftBeforeFileSizeCheck <= 0) checkCurrentFileSize()
            } catch (_: IOException) {
                switchToNewFile()
            }
        }
    }

    private fun writeLine(line: String) {
        currentFileWriter?.appendLine(line)
        linesLeftBeforeFileSizeCheck -= 1
    }

    private fun checkCurrentFileSize() {
        val file = currentFile
        if (file == null || !file.isFile || file.length() > fileMaxSizeBytes) {
            switchToNewFile()
        }
        linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK
    }

    private fun switchToNewFile() {
        closeCurrentFile()
        createNewFile()
        deleteOldFiles()
    }

    private fun closeCurrentFile() {
        try {
            currentFileWriter?.close()
        } catch (_: IOException) {}
        currentFile = null
        currentFileWriter = null
    }

    private fun createNewFile() {
        ensureDirectory(directory)
        val fileName = createFileName()
        val file = File(directory, "$fileName.$FILE_EXTENSION")
        if (file.exists()) file.delete()
        val fileWriter = file.bufferedWriter()
        currentFile = file
        currentFileWriter = fileWriter
    }

    private fun deleteOldFiles() {
        val files = directory.listFiles()
        if (files == null || files.size <= fileMaxCount) return
        files.sortBy(File::lastModified)
        val overflow = files.size - fileMaxCount
        files.take(overflow).forEach { it.delete() }
    }

    private fun createFileName(): String {
        val date = Date()
        val formatter = SimpleDateFormat(FILE_DATE_TIME_FORMAT, Locale.US)
        return formatter.format(date)
    }

    private fun clearLogcat() {
        Runtime.getRuntime().exec(ShellCommand.LOGCAT_CLEAR)
    }

    private fun runLogcat(): Process {
        return Runtime.getRuntime().exec(ShellCommand.LOGCAT_RUN)
    }

    companion object {
        const val FILE_MIME_TYPE = "text/plain"

        private const val FILE_EXTENSION = "txt"
        private const val FILE_DATE_TIME_FORMAT = "dd.MM.yyyy--HH.mm.ss"
        private const val WRITTEN_LINES_PER_FILE_SIZE_CHECK = 100

        private const val TAG = "LogPecker"
    }

}
