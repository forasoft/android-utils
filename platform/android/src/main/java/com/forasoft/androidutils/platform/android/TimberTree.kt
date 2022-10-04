package com.forasoft.androidutils.platform.android

import timber.log.Timber

/**
 * Adds a new customizable logging tree.
 *
 * @param onLog callback to be invoked for all Timber logs.
 */
@Suppress("Unused")
inline fun Timber.Forest.plantTree(
    crossinline onLog: (priority: Int, tag: String?, message: String, throwable: Throwable?) -> Unit,
) {
    val tree = object : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            onLog(priority, tag, message, t)
        }
    }
    this.plant(tree)
}
