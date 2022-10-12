package com.forasoft.androidutils.logpecker.utils

import android.os.Build
import android.os.FileObserver
import androidx.annotation.RequiresApi
import java.io.File

/**
 * Observes changes in a list of files in the given directory.
 *
 * Note: this class uses combination of [FileObserver.CREATE], [FileObserver.DELETE],
 * [FileObserver.MOVED_TO] and [FileObserver.MOVED_FROM] flags as event mask.
 */
internal class DirectoryObserver : FileObserver {

    private val onFileListChanged: () -> Unit

    /**
     * Creates [DirectoryObserver] for the given directory.
     *
     * @param onFileListChanged callback to be invoked when the file list changed.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(directory: File, onFileListChanged: () -> Unit) :
            super(directory, FILE_LIST_CHANGED_MASK) {
        this.onFileListChanged = onFileListChanged
    }

    /**
     * Creates [DirectoryObserver] for the given directory path.
     *
     * @param onFileListChanged callback to be invoked when the file list changed.
     */
    @Suppress("Deprecation")
    constructor(directoryPath: String, onFileListChanged: () -> Unit) :
            super(directoryPath, FILE_LIST_CHANGED_MASK) {
        this.onFileListChanged = onFileListChanged
    }

    override fun onEvent(event: Int, path: String?) {
        onFileListChanged()
    }

    companion object {
        private const val FILE_LIST_CHANGED_MASK = CREATE or DELETE or MOVED_TO or MOVED_FROM
    }

}
