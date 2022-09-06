package com.forasoft.androidutils.platform.android

import android.content.Context

/**
 * Converts the given value in dp unit to an equivalent value in pixels.
 */
fun Context.dpToPx(dp: Int): Float = dp * resources.displayMetrics.density

/**
 * Converts the given value in dp unit to an equivalent value in pixels.
 */
fun Context.dpToPx(dp: Float): Float = dp * resources.displayMetrics.density

/**
 * Converts the given value in pixels to an equivalent value in dp unit.
 */
fun Context.pxToDp(px: Int): Float = px / resources.displayMetrics.density

/**
 * Converts the given value in pixels to an equivalent value in dp unit.
 */
fun Context.pxToDp(px: Float): Float = px / resources.displayMetrics.density
