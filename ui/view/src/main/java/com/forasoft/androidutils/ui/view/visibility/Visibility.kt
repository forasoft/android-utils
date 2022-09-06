package com.forasoft.androidutils.ui.view.visibility

import android.view.View
import androidx.core.view.isVisible

/**
 * Sets visibility to [targetVisibility] and alpha to `0` for this [View].
 */
fun View.hideImmediately(targetVisibility: Int = View.GONE) {
    this.visibility = targetVisibility
    this.alpha = 0f
}

/**
 * Sets visibility to [View.VISIBLE] and alpha to `1` for this [View].
 */
fun View.showImmediately() {
    this.isVisible = true
    this.alpha = 1f
}
