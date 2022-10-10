package com.forasoft.androidutils.logpecker

import android.content.Context
import android.util.Log
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
    private val fileMaxSize =
        context.resources.getInteger(R.integer.forasoftandroidutils_log_pecker_file_max_size_bytes)

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private val directory = context.getLogsDirectory()

    private var currentFile: File? = null
    private var currentFileWriter: BufferedWriter? = null

    private var linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK

    fun start() {
        Log.v(TAG, "LogPecker is running")
        coroutineScope.launch(Dispatchers.IO) {
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
            try {
                writeLine(line)
                if (linesLeftBeforeFileSizeCheck <= 0) checkCurrentFileSize()
            } catch (_: IOException) {
                closeCurrentFile()
                createNewFile()
                deleteOldFiles()
            }
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
            closeCurrentFile()
            createNewFile()
            deleteOldFiles()
        }
        linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK
    }

    private fun closeCurrentFile() {
        try {
            currentFileWriter?.close()
        } catch (_: IOException) {}
        currentFile = null
        currentFileWriter = null
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

    companion object {
        private const val FILE_DATE_TIME_FORMAT = "dd.MM.yyyy--HH:mm:ss"
        private const val WRITTEN_LINES_PER_FILE_SIZE_CHECK = 100
        private const val TAG = "LogPecker"
    }

}
