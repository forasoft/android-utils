package com.forasoft.androidutils.logpecker

import android.os.Build
import android.os.FileObserver
import androidx.annotation.RequiresApi
import java.io.File

class DirectoryObserver : FileObserver {

    private val onFileListChanged: () -> Unit

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(directory: File, onFileListChanged: () -> Unit) :
            super(directory, FILE_LIST_CHANGED_MASK) {
        this.onFileListChanged = onFileListChanged
    }

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
