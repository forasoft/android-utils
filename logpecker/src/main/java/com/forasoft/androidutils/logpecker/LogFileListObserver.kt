package com.forasoft.androidutils.logpecker

import android.os.Build
import android.os.FileObserver
import androidx.annotation.RequiresApi
import java.io.File

class LogFileListObserver : FileObserver {

    private var onFileListChanged: () -> Unit

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(file: File, onFileListChanged: () -> Unit) : super(file) {
        this.onFileListChanged = onFileListChanged
    }

    @Suppress("Deprecation")
    constructor(path: String, onFileListChanged: () -> Unit) : super(path) {
        this.onFileListChanged = onFileListChanged
    }

    override fun onEvent(event: Int, path: String?) {
        if (event == CREATE || event == DELETE || event == MOVED_TO || event == MOVED_FROM) {
            onFileListChanged()
        }
    }

}
