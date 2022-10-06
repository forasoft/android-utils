package com.forasoft.androidutils.logpecker.ui

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.forasoft.androidutils.logpecker.R

internal class LogPeckerActivity : Activity() {

    private lateinit var title: TextView
    private lateinit var fileList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forasoftandroidutils_activity_log_pecker)
        setUpViews()
    }

    private fun setUpViews() {
        title = findViewById(R.id.forasoftandroidutils_log_pecker_activity_title)
        fileList = findViewById(R.id.forasoftandroidutils_log_pecker_activity_file_list)

        // Set up title
        val appName = getString(applicationInfo.labelRes)
        title.text = getString(R.string.forasoftandroidutils_log_pecker_activity_title, appName)
    }

}
