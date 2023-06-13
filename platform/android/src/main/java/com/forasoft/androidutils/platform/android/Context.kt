package com.forasoft.androidutils.platform.android

import android.app.Activity
import android.content.ClipData
import android.content.ClipData.Item
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.FileProvider
import timber.log.Timber
import java.io.File

/**
 * Goes up the [ContextWrapper] hierarchy and returns the first [Activity] found.
 * If none of the [ContextWrapper]s of the current context are Activity, returns `null`.
 */
fun Context.getActivity(): Activity? {
    var context: Context? = this
    while (true) {
        when (context) {
            is Activity -> return context
            is ContextWrapper -> context = context.baseContext
            null -> return null
        }
    }
}

/**
 * Opens the given [url] in the browser.
 *
 * @param url link to open.
 * @return `true` if the browser has been successfully opened, `false` otherwise.
 */
@Suppress("Unused")
fun Context.openUrl(url: String): Boolean {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val isActivityStarted = this.tryStartActivity(intent)
    if (!isActivityStarted) {
        Timber.tag("Context.openBrowser").w("Activity that can open URL is not found")
    }
    return isActivityStarted
}

/**
 * Opens the application settings screen.
 *
 * @return `true` if settings screen has been successfully opened, `false` otherwise.
 */
@Suppress("Unused")
fun Context.openApplicationSettings(): Boolean {
    val packageName = this.packageName
    val uri = Uri.fromParts("package", packageName, null)
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = uri
    }
    val isActivityStarted = this.tryStartActivity(intent)
    if (!isActivityStarted) {
        Timber.tag("Context.openApplicationSettings")
            .w("Activity that can open application settings is not found")
    }
    return isActivityStarted
}

/**
 * Prompts the user to view the [File] in one of the applications offered in the chooser.
 *
 * @param file file to view.
 * @param fileProviderAuthority the authority of a FileProvider defined in a `<provider>`
 * element in your app's manifest.
 * @param mimeType optional file MIME type.
 * @param chooserTitle optional title that will be displayed in the chooser.
 */
@Suppress("Unused")
fun Context.viewFile(
    file: File,
    fileProviderAuthority: String,
    mimeType: String = "*/*",
    chooserTitle: String? = null,
) {
    val uri = FileProvider.getUriForFile(this, fileProviderAuthority, file)
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        setDataAndType(uri, mimeType)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, chooserTitle))
}

/**
 * Prompts the user to share [File]s via one of the applications offered in the chooser.
 *
 * @param files files to share.
 * @param fileProviderAuthority the authority of a FileProvider defined in a `<provider>`
 * element in your app's manifest.
 * @param mimeType optional files MIME type.
 * @param label user-readable label for shared data
 */
@Suppress("Unused")
fun Context.shareFiles(
    files: List<File>,
    fileProviderAuthority: String,
    mimeType: String = "*/*",
    label: String? = null,
) {
    if (files.isEmpty()) return

    val contentResolver = this.contentResolver
    val uris = files.map {
        FileProvider.getUriForFile(this, fileProviderAuthority, it)
    }
    val clipData = ClipData.newUri(
        /* resolver = */ contentResolver,
        /* label = */ label,
        /* uri = */ uris.first()
    )
    val intent = Intent().apply {
        if (uris.size == 1) {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uris.firstOrNull())
        } else {
            action = Intent.ACTION_SEND_MULTIPLE
            uris.drop(1).forEach { clipData.addItem(Item(it)) }
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.clipData = clipData
        }
        type = mimeType
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(Intent.createChooser(intent, label))
}

private fun Context.tryStartActivity(intent: Intent): Boolean {
    return if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        true
    } else {
        false
    }
}
