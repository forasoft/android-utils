package com.forasoft.androidutils.kotlin.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate

/**
 * Class that holds [Job] and provides **thread-safe** methods to interact with it.
 *
 * Underlying read-only [Job] can be retrieved with [job] property.
 */
public class JobHolder {

    private val _job = MutableStateFlow<Job?>(null)

    /**
     * Current job.
     */
    public val job: Job?
        get() = _job.value

    /**
     * Replaces the current job with a new one **and cancels the current one**. This method is
     * **thread-safe**.
     *
     * If the cancellation of the current job is not needed, use [replaceWith] method.
     *
     * @param job [Job] to interrupt the current one with.
     * @see [replaceWith]
     */
    public infix fun interruptWith(job: Job?) {
        _job.getAndUpdate { job }?.cancel()
    }

    /**
     * Assigns the given job to a current one. This method is **thread-safe**.
     *
     * Note: this method **does not cancel the current job**. If the cancellation of the
     * current job is required, use [interruptWith] method.
     *
     * @param job [Job] to replace the current one with.
     * @see [interruptWith]
     */
    public infix fun replaceWith(job: Job?) {
        _job.value = job
    }

    public companion object

}
