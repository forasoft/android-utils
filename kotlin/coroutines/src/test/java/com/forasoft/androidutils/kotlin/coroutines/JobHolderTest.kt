package com.forasoft.androidutils.kotlin.coroutines

import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class JobHolderTest {

    private lateinit var jobHolder: JobHolder

    @Before
    fun before() {
        jobHolder = JobHolder()
    }

    @Test
    fun job_returnsCurrentJob() = runTest {
        val job = testJob()
        jobHolder interruptWith job

        val currentJob = jobHolder.job

        Truth.assertThat(currentJob).isEqualTo(job)
    }

    @Test
    fun interruptWith_replacesCurrentJob() = runTest {
        val job1 = testJob()
        jobHolder interruptWith job1

        val job2 = testJob()
        jobHolder interruptWith job2

        val currentJob = jobHolder.job
        Truth.assertThat(currentJob).isEqualTo(job2)
        Truth.assertThat(currentJob).isNotEqualTo(job1)
    }

    @Test
    fun interruptWith_cancelsCurrentJob() = runTest {
        val job1 = testJob()
        jobHolder interruptWith job1

        val job2 = testJob()
        jobHolder interruptWith job2

        Truth.assertThat(job1.isCancelled).isTrue()
        Truth.assertThat(job2.isActive).isTrue()
    }

    @Test
    fun replaceWith_replacesCurrentJob() = runTest {
        val job1 = testJob()
        jobHolder replaceWith job1

        val job2 = testJob()
        jobHolder replaceWith job2

        val currentJob = jobHolder.job
        Truth.assertThat(currentJob).isEqualTo(job2)
        Truth.assertThat(currentJob).isNotEqualTo(job1)
    }

    @Test
    fun replaceWith_doesNotCancelCurrentJob() = runTest {
        val job1 = testJob()
        jobHolder replaceWith job1

        val job2 = testJob()
        jobHolder replaceWith job2

        Truth.assertThat(job1.isActive).isTrue()
        Truth.assertThat(job2.isActive).isTrue()
    }

    private fun CoroutineScope.testJob(): Job = launch { delay(1.days) }

}
