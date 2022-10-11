package com.forasoft.androidutils.logpecker.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.forasoft.androidutils.logpecker.R

internal fun addDynamicShortcut(context: Context) {
    val addDynamicShortcut = context.resources
        .getBoolean(R.bool.forasoftandroidutils_log_pecker_is_dynamic_shortcut_enabled)
    if (!addDynamicShortcut) return

    val firstMainActivity = getFirstMainActivity(context) ?: return
    val targetActivity = ComponentName(firstMainActivity.packageName, firstMainActivity.name)

    val shortcut = createShortcut(context, targetActivity)
    ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
}

private fun getFirstMainActivity(context: Context): ActivityInfo? {
    val packageManager = context.packageManager

    val mainIntent = Intent().apply {
        action = Intent.ACTION_MAIN
        addCategory(Intent.CATEGORY_LAUNCHER)
        setPackage(context.packageName)
    }
    val activities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(mainIntent, PackageManager.ResolveInfoFlags.of(0))
    } else {
        @Suppress("Deprecation")
        packageManager.queryIntentActivities(mainIntent, 0)
    }
        .filter { it.activityInfo.name != "com.forasoft.androidutils.logpecker.ui.LogPeckerActivity" }

    return activities.firstOrNull()?.activityInfo
}

private fun createShortcut(context: Context, targetActivity: ComponentName): ShortcutInfoCompat {
    val applicationName = context.getString(context.applicationInfo.labelRes)

    val id = context.getString(R.string.forasoftandroidutils_log_pecker_dynamic_shortcut_id)
    val shortLabel =
        context.getString(R.string.forasoftandroidutils_log_pecker_dynamic_shortcut_short_label)
    val longLabel = context.getString(
        R.string.forasoftandroidutils_log_pecker_dynamic_shortcut_long_label,
        applicationName,
    )
    val icon = IconCompat.createWithResource(
        context,
        R.drawable.forasoftandroidutils_log_pecker_ic_logo_24,
    )
    val intent = createLogPeckerActivityIntent(context)

    return ShortcutInfoCompat.Builder(context, id)
        .setShortLabel(shortLabel)
        .setLongLabel(longLabel)
        .setIcon(icon)
        .setActivity(targetActivity)
        .setIntent(intent)
        .build()
}

private fun createLogPeckerActivityIntent(context: Context): Intent {
    return Intent(context, LogPeckerActivity::class.java).apply {
        // Source: https://github.com/square/leakcanary/blob/c7a0ae90063b8e33013f64e5c0907f60852b9f83/leakcanary-android-core/src/main/java/leakcanary/internal/InternalLeakCanary.kt#L286
        action = "Dummy Action because Android is stupid. Thanks to LeakCanary."
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
}
