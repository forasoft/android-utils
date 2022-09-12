@file:Suppress("unused")

package com.forasoft.androidutils.platform.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Opens the given [url] in the browser.
 *
 * @param url link to open.
 * @return `true` if the browser has been successfully opened, `false` otherwise.
 */
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

/**
 * Opens the application settings in the system.
 */
fun Context.openApplicationSettings() {
    val packageName = this.packageName
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}
