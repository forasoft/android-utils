package com.forasoft.androidutils.logpecker.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.forasoft.androidutils.logpecker.R
import com.forasoft.androidutils.logpecker.getLogsDirectory
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
        val title = view
            .findViewById<TextView>(R.id.forasoftandroidutils_log_pecker_file_item_title)
        title.text = file.name
    }

}
