package com.forasoft.androidutils.logpecker.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.forasoft.androidutils.logpecker.DirectoryObserver
import com.forasoft.androidutils.logpecker.LogPecker
import com.forasoft.androidutils.logpecker.R
import com.forasoft.androidutils.logpecker.utils.fileProviderAuthority
import com.forasoft.androidutils.logpecker.utils.getLogsDirectory
import com.forasoft.androidutils.logpecker.utils.shareFiles
import com.forasoft.androidutils.logpecker.utils.viewFile
import java.io.File

/**
 * Main LogPecker activity that presents a list of stored log files.
 */
internal class LogPeckerActivity : Activity() {

    private val logsDirectory by lazy { getLogsDirectory(this) }

    /**
     * [DirectoryObserver] observes changes in the list of log files.
     */
    private val logsDirectoryObserver by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            DirectoryObserver(logsDirectory, onFileListChanged = ::refreshFileList)
        } else {
            DirectoryObserver(logsDirectory.absolutePath, onFileListChanged = ::refreshFileList)
        }
    }

    private val title: TextView by lazy {
        findViewById(R.id.forasoftandroidutils_log_pecker_activity_title)
    }
    private val refresh: ImageButton by lazy {
        findViewById(R.id.forasoftandroidutils_log_pecker_activity_refresh)
    }
    private val fileList: ListView by lazy {
        findViewById(R.id.forasoftandroidutils_log_pecker_file_list)
    }

    private val fileListAdapter by lazy {
        SimpleListAdapter(
            itemLayoutResId = R.layout.forasoftandroidutils_log_pecker_file_item,
            bindItem = { view, item -> bindFile(view, item) },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forasoftandroidutils_activity_log_pecker)
        setUpUi()
    }

    override fun onStart() {
        super.onStart()
        removeEmptyFiles()
        refreshFileList()
        logsDirectoryObserver.startWatching()
    }

    override fun onStop() {
        super.onStop()
        logsDirectoryObserver.stopWatching()
    }

    private fun setUpUi() {
        val appName = getString(applicationInfo.labelRes)
        title.text = getString(R.string.forasoftandroidutils_log_pecker_activity_title, appName)
        refresh.setOnClickListener { refreshFileList() }

        fileList.adapter = fileListAdapter
    }

    /**
     * Binds the given log file item view to the log file.
     */
    private fun bindFile(view: View, file: File) {
        val title: TextView? =
            view.findViewById(R.id.forasoftandroidutils_log_pecker_file_item_title)
        val share: ImageButton? =
            view.findViewById(R.id.forasoftandroidutils_log_pecker_file_item_share)
        val delete: ImageButton? =
            view.findViewById(R.id.forasoftandroidutils_log_pecker_file_item_delete)

        title?.text = file.name
        view.setOnClickListener {
            if (!file.isFile) return@setOnClickListener
            viewFile(file, fileProviderAuthority, LogPecker.FILE_MIME_TYPE)
        }
        share?.setOnClickListener {
            if (!file.isFile) return@setOnClickListener
            shareFiles(listOf(file), fileProviderAuthority)
        }
        delete?.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.forasoftandroidutils_log_pecker_delete_file_question)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    file.delete()
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }

    /**
     * Refresh the list of log files.
     */
    private fun refreshFileList() {
        val files = logsDirectory.listFiles() ?: return
        files.sortBy(File::lastModified)
        runOnUiThread {
            fileListAdapter.submitList(files.toList())
        }
    }

    /**
     * Removes empty log files.
     *
     * Used mostly for deleting files that were created when the application was started due
     * to launch of LogPecker activity.
     */
    private fun removeEmptyFiles() {
        val files = logsDirectory.listFiles() ?: return
        files.forEach {
            if (it.length() == 0L) it.delete()
        }
    }

}
