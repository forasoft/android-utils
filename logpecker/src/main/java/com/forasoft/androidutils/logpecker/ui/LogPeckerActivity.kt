package com.forasoft.androidutils.logpecker.ui

import android.app.Activity
import android.os.Bundle
import com.forasoft.androidutils.logpecker.R

internal class LogPeckerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forasoftandroidutils_activity_log_pecker)
    }

}
