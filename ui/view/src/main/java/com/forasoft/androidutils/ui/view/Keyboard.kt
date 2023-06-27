package com.forasoft.androidutils.ui.view

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

/**
 * Requests to show a keyboard with given [flags].
 *
 * @param flags additional operating flags required by [InputMethodManager.showSoftInput].
 */
fun View.showKeyboard(flags: Int = InputMethodManager.SHOW_IMPLICIT) {
    val inputMethodManager = this.context.getSystemService<InputMethodManager>()
    inputMethodManager?.showSoftInput(this, flags)
}

/**
 * Requests to hide a keyboard with given [flags].
 *
 * @param flags additional operating flags required by [InputMethodManager.hideSoftInputFromWindow].
 */
fun View.hideKeyboard(flags: Int = 0) {
    val inputMethodManager = this.context.getSystemService<InputMethodManager>()
    inputMethodManager?.hideSoftInputFromWindow(this.windowToken, flags)
}
