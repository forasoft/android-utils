package com.forasoft.androidutils.platform.android

import android.os.SystemClock
import androidx.annotation.MainThread
import kotlin.time.Duration

/**
 * Class that allows to throttle operations with a certain delay. If code is wrapped
 * in [throttle] method, then each next call will be ignored if less time has passed since
 * the previous call than the specified delay.
 */
@MainThread
class Throttler(delay: Duration) {

    private val delayMillis = delay.inWholeMilliseconds
    private var lastOperationTimestamp = 0L

    /**
     * Throttle [operation] with the delay. Each next call will be ignored if less time
     * has passed since the previous call than the specified delay.
     */
    fun throttle(operation: () -> Unit) {
        if (SystemClock.elapsedRealtime() - lastOperationTimestamp > delayMillis) {
            lastOperationTimestamp = SystemClock.elapsedRealtime()
            operation()
        }
    }

}
