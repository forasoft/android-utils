package com.forasoft.androidutils.logpecker.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.forasoft.androidutils.logpecker.R
import com.forasoft.androidutils.logpecker.getLogsDirectory
import com.forasoft.androidutils.logpecker.shareFiles
import com.forasoft.androidutils.logpecker.utils.fileProviderAuthority
import com.forasoft.androidutils.logpecker.viewFile
import java.io.File

internal class LogPeckerActivity : Activity() {

    private val logsDirectory by lazy { getLogsDirectory() }

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
        setUpTitle()
        setUpFileList()
    }

    override fun onStart() {
        super.onStart()
        refreshFileList()
    }

    private fun setUpTitle() {
        val appName = getString(applicationInfo.labelRes)
        title.text = getString(R.string.forasoftandroidutils_log_pecker_activity_title, appName)
        refresh.setOnClickListener { refreshFileList() }
    }

    private fun setUpFileList() {
        fileList.adapter = fileListAdapter
    }

    private fun refreshFileList() {
        val files = logsDirectory.listFiles() ?: return
        files.sortBy(File::lastModified)
        fileListAdapter.submitList(files.toList())
    }

    private fun bindFile(view: View, file: File) {
        val title =
            view.findViewById<TextView>(R.id.forasoftandroidutils_log_pecker_file_item_title)
        val share =
            view.findViewById<ImageButton>(R.id.forasoftandroidutils_log_pecker_file_item_share)
        val delete =
            view.findViewById<ImageButton>(R.id.forasoftandroidutils_log_pecker_file_item_delete)

        title.text = file.name
        view.setOnClickListener {
            viewFile(file, fileProviderAuthority, "text/plain")
        }
        share.setOnClickListener {
            shareFiles(listOf(file), fileProviderAuthority)
        }
        delete.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.forasoftandroidutils_log_pecker_delete_file_question)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    file.delete()
                    refreshFileList()
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                .show()
        }
    }

}
