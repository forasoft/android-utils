package com.forasoft.androidutils.platform.android

import android.content.Context
import android.content.Intent
import android.net.Uri

// TODO: Write documentation
fun Context.openBrowser(url: String): Boolean {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    return if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        true
    } else {
        false
    }
}
