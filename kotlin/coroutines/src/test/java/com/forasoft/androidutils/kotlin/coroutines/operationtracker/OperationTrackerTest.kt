package com.forasoft.androidutils.kotlin.coroutines.operationtracker

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.hours

@ExperimentalCoroutinesApi
class OperationTrackerTest {

    private lateinit var tracker: OperationTracker

    private object Key1 : OperationKey
    private object Key2 : OperationKey

    @Before
    fun before() {
        tracker = OperationTracker()
    }

    @Test
    fun track_returnsResult() = runTest {
        val operation = { 10 }

        val result = tracker.track(Key1) { operation() }

        assertThat(result).isEqualTo(operation())
    }

    @Test
    fun isOperationOngoing_whenOperationIsOngoing_returnsTrue() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }

        launch {
            delay(5.hours)
            assertThat(isKey1Ongoing()).isTrue()
        }
    }

    @Test
    fun isOperationOngoing_whenOperationIsCompleted_returnsFalse() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }

        launch {
            delay(15.hours)
            assertThat(isKey1Ongoing()).isFalse()
        }
    }

    @Test
    fun isOperationOngoing_whenOuterCoroutineIsCanceled_returnsFalse() = runTest {
        val job = launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }
        launch {
            delay(5.hours)
            job.cancel()
        }

        launch {
            delay(8.hours)
            assertThat(isKey1Ongoing()).isFalse()
        }
    }

    @Test
    fun isOperationOngoing_whenMultipleOperationsWithSameKeyAreOngoing_returnsTrue() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }
        launch {
            tracker.track(Key1) {
                delay(20.hours)
            }
        }

        launch {
            delay(8.hours)
            assertThat(isKey1Ongoing()).isTrue()
        }
        launch {
            delay(18.hours)
            assertThat(isKey1Ongoing()).isTrue()
        }
    }

    @Test
    fun isOperationOngoing_whenMultipleOperationsWithDifferentKeysAreOngoing_returnsTrue() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }
        launch {
            tracker.track(Key2) {
                delay(10.hours)
            }
        }

        launch {
            delay(8.hours)
            assertThat(isKey1Ongoing()).isTrue()
            assertThat(isKey2Ongoing()).isTrue()
        }
    }

    @Test
    fun isOperationOngoing_whenMultipleKeysArePassedAndAnyOfThemIsOngoing_returnsTrue() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }
        launch {
            tracker.track(Key2) {
                delay(20.hours)
            }
        }

        launch {
            delay(15.hours)
            val isAnyKeyOngoing = tracker.isOperationOngoing(Key1, Key2).first()
            assertThat(isAnyKeyOngoing).isTrue()
        }
    }

    @Test
    fun ongoingOperationKeys_whenOperationIsOngoing_containsKey() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }

        launch {
            delay(8.hours)
            val ongoingOperationKeys = tracker.ongoingOperationKeys.first()
            assertThat(ongoingOperationKeys).contains(Key1)
        }
    }

    @Test
    fun ongoingOperationKeys_whenOperationIsCompleted_doesNotContainKey() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }

        launch {
            delay(12.hours)
            val ongoingOperationKeys = tracker.ongoingOperationKeys.first()
            assertThat(ongoingOperationKeys).doesNotContain(Key1)
        }
    }

    @Test
    fun ongoingOperationKeys_whenOuterCoroutineIsCanceled_doesNotContainKey() = runTest {
        val job = launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }
        launch {
            delay(5.hours)
            job.cancel()
        }

        launch {
            delay(8.hours)
            val ongoingOperationKeys = tracker.ongoingOperationKeys.first()
            assertThat(ongoingOperationKeys).doesNotContain(Key1)
        }
    }

    @Test
    fun ongoingOperationKeys_whenMultipleOperationsAreOngoing_containsAllKeys() = runTest {
        launch {
            tracker.track(Key1) {
                delay(10.hours)
            }
        }
        launch {
            tracker.track(Key2) {
                delay(10.hours)
            }
        }

        launch {
            delay(8.hours)
            val ongoingOperationKeys = tracker.ongoingOperationKeys.first()
            assertThat(ongoingOperationKeys).contains(Key1)
            assertThat(ongoingOperationKeys).contains(Key2)
        }
    }

    private suspend fun isKey1Ongoing(): Boolean {
        return tracker.isOperationOngoing(Key1).first()
    }

    private suspend fun isKey2Ongoing(): Boolean {
        return tracker.isOperationOngoing(Key2).first()
    }

}
