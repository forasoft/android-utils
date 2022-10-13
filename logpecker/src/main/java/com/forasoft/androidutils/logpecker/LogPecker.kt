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

/**
 * LogPecker writes the application logs and saves them on the device.
 *
 * Use [start] method to start the process.
 */
internal class LogPecker(context: Context) {

    /**
     * The maximum number of files that can be stored on the device at the same time.
     */
    private val fileMaxCount =
        context.resources.getInteger(R.integer.forasoftandroidutils_log_pecker_file_max_count)

    /**
     * The maximum size of a single file in bytes.
     */
    private val fileMaxSizeBytes =
        context.resources.getInteger(R.integer.forasoftandroidutils_log_pecker_file_max_size_bytes)

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private val directory = getLogsDirectory(context)

    private var currentFile: File? = null
    private var currentFileWriter: BufferedWriter? = null

    /**
     * Indicates how many lines are left to write before checking if the current file is larger
     * than the [fileMaxSizeBytes] limit.
     */
    private var linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK

    /**
     * Starts to write the application logs and save them on the device.
     */
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

    /**
     * Writes lines to the current file and switches to a new one when the current file size
     * exceeds the [fileMaxSizeBytes] limit.
     */
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

    /**
     * Writes the given line to the current file.
     */
    private fun writeLine(line: String) {
        currentFileWriter?.appendLine(line)
        linesLeftBeforeFileSizeCheck -= 1
    }

    /**
     * Checks if the current file size is larger than the [fileMaxSizeBytes] limit.
     *
     * If the file size exceeds the limit (or the file is missing), a new file is created.
     */
    private fun checkCurrentFileSize() {
        val file = currentFile
        if (file == null || !file.isFile || file.length() > fileMaxSizeBytes) {
            switchToNewFile()
        }
        linesLeftBeforeFileSizeCheck = WRITTEN_LINES_PER_FILE_SIZE_CHECK
    }

    /**
     * Closes the current file and creates a new one. Also removes old files if the number of
     * file exceeds [fileMaxCount] limit.
     */
    private fun switchToNewFile() {
        closeCurrentFile()
        createNewFile()
        deleteOldFiles()
    }

    /**
     * Closes the current file.
     */
    private fun closeCurrentFile() {
        try {
            currentFileWriter?.close()
        } catch (_: IOException) {}
        currentFile = null
        currentFileWriter = null
    }

    /**
     * Creates a new file for saving logs.
     */
    private fun createNewFile() {
        ensureDirectory(directory)
        val fileName = createFileName()
        val file = File(directory, "$fileName.$FILE_EXTENSION")
        if (file.exists()) file.delete()
        val fileWriter = file.bufferedWriter()
        currentFile = file
        currentFileWriter = fileWriter
    }

    /**
     * Removes the oldest files if the number of log files exceeds [fileMaxCount] limit.
     */
    private fun deleteOldFiles() {
        val files = directory.listFiles()
        if (files == null || files.size <= fileMaxCount) return
        files.sortBy(File::lastModified)
        val overflow = files.size - fileMaxCount
        files.take(overflow).forEach { it.delete() }
    }

    /**
     * Generates a new log file name based on the current date and time.
     */
    private fun createFileName(): String {
        val date = Date()
        val formatter = SimpleDateFormat(FILE_DATE_TIME_FORMAT, Locale.US)
        return formatter.format(date)
    }

    /**
     * Clears logcat buffer.
     */
    private fun clearLogcat() {
        Runtime.getRuntime().exec(LogcatCommand.CLEAR)
    }

    /**
     * Runs logcat and returns its [Process].
     */
    private fun runLogcat(): Process {
        return Runtime.getRuntime().exec(LogcatCommand.RUN)
    }

    companion object {
        /**
         * Log file MIME type.
         */
        const val FILE_MIME_TYPE = "text/plain"

        private const val FILE_EXTENSION = "txt"
        private const val FILE_DATE_TIME_FORMAT = "dd.MM.yyyy--HH.mm.ss"
        private const val WRITTEN_LINES_PER_FILE_SIZE_CHECK = 100

        private const val TAG = "LogPecker"
    }

}
