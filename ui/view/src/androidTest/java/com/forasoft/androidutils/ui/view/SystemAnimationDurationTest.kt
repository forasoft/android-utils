package com.forasoft.androidutils.ui.view

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class SystemAnimationDurationTest {

    private val context: Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun getSystemAnimationDuration_whenShort_returnsCorrectValue() {
        val duration = context.getSystemAnimationDuration(SystemAnimationDuration.SHORT)

        val durationMillis = duration.inWholeMilliseconds

        val correctMillis = context.resources.getInteger(android.R.integer.config_shortAnimTime)
        assertThat(durationMillis).isEqualTo(correctMillis)
    }

    @Test
    fun getSystemAnimationDuration_whenMedium_returnsCorrectValue() {
        val duration = context.getSystemAnimationDuration(SystemAnimationDuration.MEDIUM)

        val durationMillis = duration.inWholeMilliseconds

        val correctMillis = context.resources.getInteger(android.R.integer.config_mediumAnimTime)
        assertThat(durationMillis).isEqualTo(correctMillis)
    }

    @Test
    fun getSystemAnimationDuration_whenLong_returnsCorrectValue() {
        val duration = context.getSystemAnimationDuration(SystemAnimationDuration.LONG)

        val durationMillis = duration.inWholeMilliseconds

        val correctMillis = context.resources.getInteger(android.R.integer.config_longAnimTime)
        assertThat(durationMillis).isEqualTo(correctMillis)
    }

}
