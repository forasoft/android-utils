package com.forasoft.androidutils.logpecker

import android.app.Activity
import android.os.Bundle

internal class LogPeckerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_pecker)
    }

}
