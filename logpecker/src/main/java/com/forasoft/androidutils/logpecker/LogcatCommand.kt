package com.forasoft.androidutils.logpecker

/**
 * Set of shell logcat commands.
 */
internal object LogcatCommand {
    /**
     * Clears logcat buffer.
     */
    const val CLEAR = "logcat -c"

    /**
     * Runs logcat.
     */
    const val RUN = "logcat"
}
