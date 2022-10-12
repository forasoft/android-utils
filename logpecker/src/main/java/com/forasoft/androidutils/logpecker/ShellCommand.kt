package com.forasoft.androidutils.logpecker

/**
 * Set of shell commands.
 */
internal object ShellCommand {
    /**
     * Clears logcat buffer.
     */
    const val LOGCAT_CLEAR = "logcat -c"

    /**
     * Runs logcat.
     */
    const val LOGCAT_RUN = "logcat"
}
