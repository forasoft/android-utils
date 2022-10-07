package com.forasoft.androidutils.logpecker.ui

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.forasoft.androidutils.logpecker.R

internal class LogPeckerActivity : Activity() {

    private val title: TextView by lazy {
        findViewById(R.id.forasoftandroidutils_log_pecker_activity_title)
    }
    private val refresh: ImageButton by lazy {
        findViewById(R.id.forasoftandroidutils_log_pecker_activity_refresh)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forasoftandroidutils_activity_log_pecker)
        setUpTitle()
    }

    private fun setUpTitle() {
        val appName = getString(applicationInfo.labelRes)
        title.text = getString(R.string.forasoftandroidutils_log_pecker_activity_title, appName)
    }

}
