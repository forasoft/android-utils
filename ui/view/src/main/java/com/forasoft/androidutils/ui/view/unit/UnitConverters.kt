package com.forasoft.androidutils.ui.view.unit

import android.content.Context

/**
 * Converts the given value in dp unit to an equivalent value in pixels.
 */
public fun Context.dpToPx(dp: Int): Float = dp * resources.displayMetrics.density

/**
 * Converts the given value in dp unit to an equivalent value in pixels.
 */
public fun Context.dpToPx(dp: Float): Float = dp * resources.displayMetrics.density

/**
 * Converts the given value in pixels to an equivalent value in dp unit.
 */
public fun Context.pxToDp(px: Int): Float = px / resources.displayMetrics.density

/**
 * Converts the given value in pixels to an equivalent value in dp unit.
 */
public fun Context.pxToDp(px: Float): Float = px / resources.displayMetrics.density
